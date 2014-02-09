package com.seanandjemma

import com.mchange.v2.c3p0.ComboPooledDataSource

object DataSourcePool {

  private val configFile = this.getClass().getResource("/c3p0.properties")
  private val datasourcePool = new ComboPooledDataSource() // uses c3p0.properties

  def getConnection() = datasourcePool.getConnection()

}