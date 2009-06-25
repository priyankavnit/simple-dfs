package org.agile.framework.hessian{
	import mx.controls.Alert;
	import mx.rpc.IResponder;

	public class DefaultHessianResponder implements IResponder{
		
		private var _result:Function;
		private var _fault:Function;
		
		private var debug:Boolean = false;
		
		public function DefaultHessianResponder(result:Function=null,fault:Function=null){
			this._result = result;
			this._fault = fault;
		}

		public function result(data:Object):void{
			if(debug){
				Alert.show("Res:"+data);
				return;
			}
			if(this._result !=null){
				this._result(data);
			}else{
				trace(data);
			}
		}
		
		public function fault(info:Object):void{
			if(debug){
				Alert.show("Err:"+info);
				return;
			}
			if(this._fault !=null){
				this._fault(info);
			}else{
				trace(info);
			}
		}
		
	}
}