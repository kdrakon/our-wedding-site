package com.seanandjemma

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.servlet.ServletHandler
import com.seanandjemma.servlet.RsvpServlet
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.handler.HandlerList

object WebServer extends App {

  val jettyServer = new Server(args(1).toInt)

  val htmlHandler = new ResourceHandler()
  htmlHandler.setResourceBase(args(0))

  val rsvpServlet = new ServletHandler()
  rsvpServlet.addServletWithMapping(classOf[RsvpServlet], "/rsvp")

  val handlers = new HandlerList()
  handlers.setHandlers((Array(htmlHandler, rsvpServlet).toArray[Handler]))
  jettyServer.setHandler(handlers)

  jettyServer.start()
  jettyServer.join()

}