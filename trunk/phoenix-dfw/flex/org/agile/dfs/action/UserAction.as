package org.agile.dfs.action {  

	public class UserAction extends BaseAction {
		private static const userService:String="basicservice";
		private static const loginService:String="LoginManager";

		public function login(userName:String, passWord:String, onResult:Function=null):void {
			invoke(loginService, "login", [userName, passWord], onResult);
		}

		public function create(user:Object):void {
			invoke(userService, "hello", null);
		}
	}
}