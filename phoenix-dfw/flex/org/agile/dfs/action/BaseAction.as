package org.agile.dfs.action {
	import org.agile.dfs.manager.DfsService;
	import org.agile.framework.action.AbstractAction;
	 
	public class BaseAction extends AbstractAction {

		public function BaseAction() {
		}

		public function invoke(service:String, method:String, args:Array, onResult:Function=null, onFault:Function=null):void {
			DfsService.invoke(service, method, args, onResult, onFault);
		}
	}
}