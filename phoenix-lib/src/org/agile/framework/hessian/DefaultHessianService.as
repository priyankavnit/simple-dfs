package org.agile.framework.hessian {
	
	import flash.utils.flash_proxy;
	
	import hessian.client.HessianService;
	
	import mx.rpc.AbstractOperation;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	
	use namespace flash_proxy; 

	dynamic public class DefaultHessianService extends HessianService { 
		
		public function DefaultHessianService(dest:String) {
			super(dest);
		}
		
		public function invoke(method:String,args:Array=null,onResult:Function=null,onFault:Function=null):void{
			var responder:IResponder = new DefaultHessianResponder(onResult,onFault); 
			var op:AbstractOperation = getOperation(getLocalName(method));
        	var tk:AsyncToken;
        	if(args==null){
        		tk = op.send();
        	}else{
        		var len:int = args.length;
				if(len==1){
					tk = op.send(args[0]); 
				}else if(len==2){
					tk = op.send(args[0],args[1]);
				}else if(len==3){
					tk = op.send(args[0],args[1],args[2]);
				}else if(len==4){
					tk = op.send(args[0],args[1],args[2],args[3]);
				}else if(len==5){
					tk = op.send(args[0],args[1],args[2],args[3],args[4]);
				}	
        	}
        	tk.addResponder(responder);	
		}
		
		override flash_proxy function callProperty(name:*, ... total:Array):* { 
			var args:Array = total[0];
			var responder:IResponder = new DefaultHessianResponder(total[2] as Function,total[3] as Function); 
			var op:AbstractOperation = getOperation(getLocalName(name));
        	var tk:AsyncToken;
        	if(args==null){
        		tk = op.send();
        	}else{
        		tk = op.send(args);
				//        		var len:int = args.length;
				//        		if(len==1){
				//        			tk = op.send(args[0]); 
				//        		}else if(len==2){
				//        			tk = op.send(args[0],args[1]);
				//        		}else if(len==3){
				//        			tk = op.send(args[0],args[1],args[2]);
				//        		}else if(len==4){
				//        			tk = op.send(args[0],args[1],args[2],args[3]);
				//        		}else if(len==5){
				//        			tk = op.send(args[0],args[1],args[2],args[3],args[4]);
				//        		}
        	}
        	tk.addResponder(responder);  
    	}
    	
    	private function getLocalName(name:Object):String{
	        if (name is QName) {
	            return QName(name).localName;
	        }
	        else {
	            return String(name);
	        }
    	}
        	
   		public function backCallProperty(method: *, ...args): * {   
     		try {  
       			this[method].apply(this, args);  
     		} finally {  
       			return methodMissing(method, args);  
     		}  
   		}   
   		
   		protected function methodMissing(method : *, args : Array) : Object {  
     		throw new Error("Method " + method + " is missing!");   
		}
	}
}