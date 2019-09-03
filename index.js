
import { NativeModules,requireNativeComponent } from 'react-native';


var BaiduFace = NativeModules.RNBaiduFace
var RNBaiduFace = requireNativeComponent('RNBaiduFace', null);

export default BaiduFace;

export {
  RNBaiduFace
}