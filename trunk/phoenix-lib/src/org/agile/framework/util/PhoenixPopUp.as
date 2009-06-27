package org.agile.framework.util {
	import flash.display.DisplayObject;

	import mx.core.IFlexDisplayObject;
	import mx.managers.PopUpManager;

	public class PhoenixPopUp extends PopUpManager {

		private static var last:IFlexDisplayObject;

		public static function createPopUp(parent:DisplayObject, className:Class, modal:Boolean=false, childList:String=null):IFlexDisplayObject {
			var pop:IFlexDisplayObject=PopUpManager.createPopUp(parent, className, modal, childList);
			last=pop;
			return pop;
		}

		public static function removePopUp(popup:IFlexDisplayObject):void {
			if (popup) {
				PopUpManager.removePopUp(popup);
				last=null;
			} else {
				if (last != null) {
					PopUpManager.removePopUp(last);
					last=null;
				}
			}
		}

		public static function centerPopUp(popUp:IFlexDisplayObject):void {
			PopUpManager.centerPopUp(popUp);
			last = popUp;
		}
	}
}
