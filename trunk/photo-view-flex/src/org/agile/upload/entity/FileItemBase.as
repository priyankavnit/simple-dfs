/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (FileItem.as).
 */

package org.agile.upload.entity {

    import flash.utils.ByteArray;
    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class FileItemBase implements IExternalizable {

        private var __initialized:Boolean = true;
        private var __detachedState:String = null;

        private var _id:Number;
        private var _modified:Date;
        private var _name:String;
        private var _rawimage:ByteArray;
        private var _thumbnail:ByteArray;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __initialized;

            var property:* = this[name];
            return (
                (!(property is FileItem) || (property as FileItem).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set modified(value:Date):void {
            _modified = value;
        }
        public function get modified():Date {
            return _modified;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set rawimage(value:ByteArray):void {
            _rawimage = value;
        }
        public function get rawimage():ByteArray {
            return _rawimage;
        }

        public function set thumbnail(value:ByteArray):void {
            _thumbnail = value;
        }
        public function get thumbnail():ByteArray {
            return _thumbnail;
        }

        public function readExternal(input:IDataInput):void {
            __initialized = input.readObject() as Boolean;
            __detachedState = input.readObject() as String;
            if (meta::isInitialized()) {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _modified = input.readObject() as Date;
                _name = input.readObject() as String;
                _rawimage = input.readObject() as ByteArray;
                _thumbnail = input.readObject() as ByteArray;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__initialized);
            output.writeObject(__detachedState);
            if (meta::isInitialized()) {
                output.writeObject(_id);
                output.writeObject(_modified);
                output.writeObject(_name);
                output.writeObject(_rawimage);
                output.writeObject(_thumbnail);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}