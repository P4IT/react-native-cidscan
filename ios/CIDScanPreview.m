//
//  CIDScanPreview.m
//  Pods
//
//  Created by Uwe Hoppe on 01.02.22.
//

#import <Foundation/Foundation.h>
#import "CIDScanPreview.h"
#import "SimpleScanner.h"

@implementation CIDScanPreview

-(id)initWithDelegate:(id<CIDScanPreviewListener>)listener {
    self = [super init];
    self.delegate = listener;
    [self initialize];
    return self;
}

-(void)initialize {
    self.scanner = [SimpleScanner getSharedObject:[[UIScreen mainScreen] bounds]];
    [self addSubview:self.scanner];
}

- (void)setOnPreviewReady:(RCTDirectEventBlock)onPreviewReady {
    _onPreviewReady = onPreviewReady;
    [self.scanner startScanner:self];
}

- (void)removeFromSuperview {
    [super removeFromSuperview];
}

- (void)onScannerStarted {
    if(self.delegate != nil) {
        [self.delegate previewReady:self];
    }
}

@end
