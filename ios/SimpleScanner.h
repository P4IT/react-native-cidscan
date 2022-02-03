//
//  CIDScanView.h
//  iOS-CaptureID-Library
//
//  Created by Uwe Hoppe on 14.09.21.
//  Copyright © 2022 P4IT GmbH & Co. KG All rights reserved.
//

#ifndef SimpleScanner_h
#define SimpleScanner_h

#import <UIKit/UIKit.h>

@protocol SimpleScannerEventListener <NSObject>
-(void)onScannerStarted;
@end

@interface SimpleScanner : UIView

+(SimpleScanner*_Nonnull) getSharedObject:(CGRect) frame;

-(void) startDecode;
-(void) startScanner:(id<SimpleScannerEventListener>_Nullable)listener;

@end
#endif /* SimpleScanner_h */
