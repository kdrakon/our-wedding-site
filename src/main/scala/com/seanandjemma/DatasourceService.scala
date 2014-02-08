package com.seanandjemma

import com.mchange.v2.c3p0.ComboPooledDataSource

object DatasourceService {

  val datasourcePool = new ComboPooledDataSource() // uses c3p0.properties

  def getConnection() = datasourcePool.getConnection()

}