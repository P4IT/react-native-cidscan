#import "React/RCTViewManager.h"
#import "SimpleScanner.h"
#import "CIDScanPreview.h"

@interface RNCIDScanView: RCTViewManager<CIDScanPreviewListener>
@end

@implementation RNCIDScanView

RCT_EXPORT_MODULE()
RCT_EXPORT_VIEW_PROPERTY(onPreviewReady, RCTDirectEventBlock)

-(UIView *)view {
    CIDScanPreview *scnview = [[CIDScanPreview alloc]initWithDelegate:self];
    return scnview;
}

- (void)previewReady:(CIDScanPreview *)preview {
  if (!preview.onPreviewReady) {
    return;
  }
    preview.onPreviewReady(nil);
}

@end
