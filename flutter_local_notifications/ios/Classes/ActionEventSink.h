//
//  ActionEventSink.h
//  Runner
//
//  Created by 홍준근 on 2021/01/04.
//  Copyright © 2021 The Chromium Authors. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>

NS_ASSUME_NONNULL_BEGIN

@interface ActionEventSink : NSObject<FlutterStreamHandler>

- (void)addItem:(NSDictionary *)item;

@end

NS_ASSUME_NONNULL_END
