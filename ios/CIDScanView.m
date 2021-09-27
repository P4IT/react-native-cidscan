#import "React/RCTViewManager.h"
#import "CIDScanView.h"

@interface RNCIDScanView: RCTViewManager
@end

@implementation RNCIDScanView

RCT_EXPORT_MODULE()

-(UIView *)view {
    CIDScanView * scview = [CIDScanView getSharedObject: [[UIScreen mainScreen] bounds]];
    [scview startScanner];
    return scview;
}
@end
