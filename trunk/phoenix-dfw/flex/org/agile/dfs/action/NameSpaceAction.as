package org.agile.dfs.action {
	import org.agile.dfs.entity.NameSpace;
	  
	public class NameSpaceAction extends BaseAction {
		private static const service:String="NameSpace"; 
 
		public function create(ns:NameSpace):void {
			invoke(service, "hello", [ns]);
		}
	}
}