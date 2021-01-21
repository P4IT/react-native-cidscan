# react-native-cidscan

React Native CaptureID Plugin

## Installation

from within your app folder run
```sh
npm install react-native-cidscan
cd ios && pod install && cd ..
```

## Usage

```js
import Cidscan from "react-native-cidscan";

// ...

const result = await Cidscan.initCaptureID(callback);

function callback(error, result) {
    if (error) {
        // do something in case of an error
    } else {
        // handle result
    }
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License
