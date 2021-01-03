require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-cidscan"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "10.0" }
  s.source       = { :git => "https://github.com/P4IT/react-native-cidscan.git", :tag => "#{s.version}" }

  
  s.source_files = "ios/**/*.{h,m,mm,a,swift}"
  s.static_framework = true
  s.vendored_libraries = 'ios/CaptureIDLibrary.a'
  s.ios.xcconfig = { "HEADER_SEARCH_PATHS" => "$(PODS_ROOT)/#{s.name}, #{File.join(File.dirname(__FILE__), 'ios')}", "LIBRARY_SEARCH_PATHS" => "#{File.join(File.dirname(__FILE__), 'ios')}" }

  s.dependency "React-Core"
end
