package org.agile.dfs.action {
	import org.agile.dfs.entity.NameSpace;

	public class NameSpaceAction extends BaseAction {
		private static const service:String="NameSpace";

		public function create(ns:NameSpace, callback:Function=null):void {
			invoke(service, "create", [ns], callback);
		}

		public function list(callback:Function=null):void {
			invoke(service, "queryAllForMap", null, callback);
		}
	}
}