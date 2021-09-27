//
//  CIDScanView.h
//  iOS-CaptureID-Library
//
//  Created by Uwe Hoppe on 14.09.21.
//  Copyright © 2021 Christian Jung. All rights reserved.
//

#ifndef CIDScanView_h
#define CIDScanView_h

#import <UIKit/UIKit.h>

@interface CIDScanView : UIView

+(CIDScanView*_Nonnull) getSharedObject:(CGRect) frame;

-(void) startDecode;
-(void) startScanner;

@end
#endif /* CIDScanView_h */
