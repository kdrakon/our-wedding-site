package com.seanandjemma

import akka.actor.ActorSystem
import akka.actor.Props
import spray.servlet.WebBoot

class RsvpServlet extends WebBoot {

  val system = ActorSystem("rsvpActorSystem")
  val serviceActor = system.actorOf(Props[RsvpActor])

}