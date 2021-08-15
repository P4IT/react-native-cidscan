//
//  CIDScanView.m
//  Cidscan
//
//  Created by Uwe Hoppe on 14.08.21.
//  Copyright © 2021 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RCTViewManager.h"

@implementation CIDScanViewManager

RCT_EXPORT_MODULE()
-(UIView *)view
{
    return [[CIDScanView alloc] init];
}
@end
