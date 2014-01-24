import AssemblyKeys._ // put this at the top of the file

assemblySettings

jarName in assembly := "wedding-server-deploy.jar"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case x => old(x)
  }
}
