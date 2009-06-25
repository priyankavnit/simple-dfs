package org.agile.dfs.manager{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.agile.framework.hessian.DefaultHessianService;
	
	public class GlobalHessianService{
		private static var url:String = "";//Configuration.hessianConnectionUrl;
		private static var services:ArrayCollection = new ArrayCollection();
		
		public function GlobalHessianService(){ 
		}
		
		public static function invoke(service:String,method:String,args:Array,onResult:Function=null,onFault:Function=null):void{
			var s:DefaultHessianService = getServiceByName(service);  
			s.invoke(method,args,function (data:Object):void{
				var e:ResultEvent = data as ResultEvent;
				var d:Object = e.result;
				if(onResult==null){
					trace("Result " + d);
				}else{
					try{
						onResult(d);
					}catch(er:Error){
						Alert.show(er.message,"调用Callback异常");
					}
				}
			},function (data:Object):void{
				if(onFault==null){
					var f:FaultEvent = data as FaultEvent;
					var msg:String  = "调用" + service + "." + method + "异常! \n" + f.message;
					Alert.show(msg,"调用服务异常");
				}else{
					onFault(data);
				}
			});
		}
		
		private static function getServiceByName(name:String):DefaultHessianService{
			var needDest:String = url + "/" + name; 
			for each (var s:DefaultHessianService in services) {
 				var dest:String = s.destination;
 				if(dest == needDest){
 					// return s;
 				}
			}		
			var service:DefaultHessianService = new DefaultHessianService(needDest);  
			// services.addItem(service);
			return service;
		}
	}
}