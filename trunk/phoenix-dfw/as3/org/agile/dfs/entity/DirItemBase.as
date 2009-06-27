/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (DirItem.as).
 */

package org.agile.dfs.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;

    [Bindable]
    public class DirItemBase implements IExternalizable {

        private var _blockNum:int;
        private var _copyNum:int;
        private var _id:String;
        private var _name:String;
        private var _parentId:String;
        private var _status:String;

        public function set blockNum(value:int):void {
            _blockNum = value;
        }
        public function get blockNum():int {
            return _blockNum;
        }

        public function set copyNum(value:int):void {
            _copyNum = value;
        }
        public function get copyNum():int {
            return _copyNum;
        }

        public function set id(value:String):void {
            _id = value;
        }
        public function get id():String {
            return _id;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set parentId(value:String):void {
            _parentId = value;
        }
        public function get parentId():String {
            return _parentId;
        }

        public function set status(value:String):void {
            _status = value;
        }
        public function get status():String {
            return _status;
        }

        public function readExternal(input:IDataInput):void {
            _blockNum = input.readObject() as int;
            _copyNum = input.readObject() as int;
            _id = input.readObject() as String;
            _name = input.readObject() as String;
            _parentId = input.readObject() as String;
            _status = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_blockNum);
            output.writeObject(_copyNum);
            output.writeObject(_id);
            output.writeObject(_name);
            output.writeObject(_parentId);
            output.writeObject(_status);
        }
    }
}