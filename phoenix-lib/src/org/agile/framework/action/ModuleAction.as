package org.agile.framework.action {
	import flash.display.DisplayObject;
	import flash.system.System;
	
	import mx.controls.Alert;
	import mx.core.Container;
	import mx.events.ModuleEvent;
	import mx.managers.IHistoryManager;
	import mx.modules.IModuleInfo;
	import mx.modules.ModuleManager;
	
	import org.agile.framework.view.Loading;


	public class ModuleAction {
		public var moduleContainer:Container;
		//当前加载的模块
		private var currentModule:IModuleInfo;
		private var historyManager:IHistoryManager;

		public function ModuleAction(con:Container){
			this.moduleContainer = con;
		}
		 
		public function loadModule(url:String):void {
			moduleContainer.removeAllChildren();
			//卸载当前模块
			if (currentModule) {
				currentModule.removeEventListener(ModuleEvent.READY, moduleReadeyHandler);
				currentModule.removeEventListener(ModuleEvent.ERROR, moduleErrorHandler);
				currentModule.unload();
			}

			//加载新模块
			currentModule=ModuleManager.getModule(url);
			currentModule.addEventListener(ModuleEvent.READY, moduleReadeyHandler);
			currentModule.addEventListener(ModuleEvent.ERROR, moduleErrorHandler);
			// currentModule.addEventListener(ModuleEvent.PROGRESS,moduleProcessHandler);        

			currentModule.load();
		}

		private function moduleReadeyHandler(e:ModuleEvent):void {
			var mod:Object=currentModule.factory.create();
			mod.setStyle("top", 0);
			mod.setStyle("left", 0);
			mod.setStyle("right", 0);
			mod.setStyle("bottom", 0);
			moduleContainer.addChild(mod as DisplayObject);
			System.gc();
		}


		private function moduleProcessHandler(e:ModuleEvent):void {
			var ld:Loading=new Loading()
			ld.visible=true;
			moduleContainer.addChild(ld);
		}

		private function moduleErrorHandler(e:ModuleEvent):void {
			Alert.show(e.errorText, "提示信息");
		}


	}
}