package com.seanandjemma

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHandler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.Handler
import spray.servlet.Initializer
import spray.servlet.Servlet30ConnectorServlet

object WebServer extends App {

  val jettyServer = new Server(args(1).toInt)

  // for HTML serving
  val htmlHandler = new ResourceHandler()
  htmlHandler.setResourceBase(args(0))

  // for Spray
  val sprayContext = new ServletContextHandler()
  sprayContext.addEventListener(new Initializer())
  val sprayServletHandler = new ServletHandler()
  sprayServletHandler.addServletWithMapping(classOf[Servlet30ConnectorServlet], "/rsvp/*")
  sprayContext.setHandler(sprayServletHandler)

  val handlers = new HandlerList()
  handlers.setHandlers((Array(htmlHandler, sprayContext).toArray[Handler]))
  jettyServer.setHandler(handlers)

  jettyServer.start()
  jettyServer.join()

}