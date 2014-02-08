package com.seanandjemma

import akka.actor.Actor

class RsvpActor extends Actor {

  def receive = {
    case None => None
  }

}