
# react-native-baidu-AI
基于百度sdk 实现的 人脸采集 组件

## Getting started

`$ npm install react-native-baidu-ai --save`

### Mostly automatic installation

`$ react-native link react-native-baidu-ai`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-baidu-ai` and add `RNMyLibrary.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMyLibrary.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNMyLibraryPackage;` to the imports at the top of the file
  - Add `new RNMyLibraryPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-baidu-ai'
  	project(':react-native-baidu-ai').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-baidu-ai/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-baidu-ai')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNMyLibrary.sln` in `node_modules/react-native-baidu-ai/windows/RNMyLibrary.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using My.Library.RNMyLibrary;` to the usings at the top of the file
  - Add `new RNMyLibraryPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import baiduAI,{RNBaiduFace} from 'react-native-baidu-ai';

// TODO: What to do with the module?
baiduAI.init()

<RNBaiduFace />

```
  
