//
//  CIDScanPreview.h
//  Pods
//
//  Created by Uwe Hoppe on 01.02.22.
//

#ifndef CIDScanPreview_h
#define CIDScanPreview_h

#import <Foundation/Foundation.h>
#import <React/RCTComponent.h>
#import "SimpleScanner.h"

@class CIDScanPreview;

@protocol CIDScanPreviewListener <NSObject>
-(void) previewReady:(CIDScanPreview*)preview;
@end

@interface CIDScanPreview : UIView<SimpleScannerEventListener>

@property (nonatomic, copy) RCTDirectEventBlock onPreviewReady;
@property id<CIDScanPreviewListener> delegate;
@property (nonatomic, strong)SimpleScanner *scanner;

-(id)initWithDelegate:(id<CIDScanPreviewListener>)listener;

@end


#endif /* CIDScanPreview_h */
