package com.seanandjemma

import akka.actor.Actor
import spray.http._
import spray.http.HttpMethods._
import spray.http.Uri.Query
import javax.activation.DataSource
import spray.httpx.unmarshalling.FormDataUnmarshallers

class RsvpActor extends Actor {

  def receive = {

    case HttpRequest(PUT, Uri(_, _, Uri.Path("/rsvp/guests/email"), query, _), _, content, _) => sender ! HttpResponse(RsvpActor.saveGuestEmail(query, content))

    case _ => sender ! HttpResponse(StatusCodes.NotFound)
  }

}

object RsvpActor {

  val saveEmailQuery = "UPDATE guest_groups SET contact_email = ? WHERE guest_group_id = ?"

  def saveGuestEmail(query: Query, content: HttpEntity): StatusCode = {

    val contactEmail = FormDataUnmarshallers.UrlEncodedFormDataUnmarshaller.apply(content) match {
      case Left(_) => None
      case Right(formData) => formData.fields.toMap.get("contact_email")
    }

    if (!contactEmail.isDefined) return StatusCodes.BadRequest

    lookupGuest(query) match {
      case None => StatusCodes.NotFound
      case Some(guestLookup) => {

        val connection = DataSourcePool.getConnection()
        try {

          val statement = connection.prepareStatement(saveEmailQuery)
          statement.setString(1, contactEmail.getOrElse(""))
          statement.setLong(2, guestLookup.guestGroup)

          !statement.execute() match {
            case true => StatusCodes.OK
            case false => StatusCodes.BadRequest
          }

        } catch {
          case _: Throwable => StatusCodes.BadRequest

        } finally {
          connection.close()
        }

      }
    }

  }

  val guestLookupQuery = "SELECT * FROM guests WHERE LOWER(last_name) = ? AND LOWER(first_name) = ?"
  case class GuestLookup(guestId: Long, guestGroup: Long)

  def lookupGuest(query: Query): Option[GuestLookup] = {

    val queryMap = query.toMap
    if (queryMap.contains("last_name") && queryMap.contains("first_name")) {

      val lastName = query.getOrElse("last_name", "").toLowerCase()
      val firstName = query.getOrElse("first_name", "").toLowerCase()

      val connection = DataSourcePool.getConnection()
      try {

        val statement = connection.prepareStatement(guestLookupQuery)
        statement.setString(1, lastName)
        statement.setString(2, firstName)

        val resultSet = statement.executeQuery()
        if (!resultSet.next()) {
          None
        } else {
          val guestId = resultSet.getLong("guest_id")
          val guestGroup = resultSet.getLong("guest_group")
          Some(GuestLookup(guestId, guestGroup))
        }

      } catch {
        case _: Throwable => None

      } finally {
        connection.close()
      }

    } else {
      None
    }

  }

}