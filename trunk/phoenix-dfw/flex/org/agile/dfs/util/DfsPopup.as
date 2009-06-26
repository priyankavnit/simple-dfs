package org.agile.dfs.util {
	import mx.core.Application;
	import mx.core.UIComponent;

	import org.agile.dfs.view.DfsDialog;
	import org.agile.framework.util.PhoenixPopUp;

	public class DfsPopup extends PhoenixPopUp {

		public static function popup(title:String, widget:UIComponent):void {
			var app:Application=Application.application as Application;
			var dialog:DfsDialog=PhoenixPopUp.createPopUp(app, DfsDialog, true) as DfsDialog; 
			dialog.widget=widget;
			dialog.title=title;
			PhoenixPopUp.centerPopUp(dialog);
		}
	}
}