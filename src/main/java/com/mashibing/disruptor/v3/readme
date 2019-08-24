修改Main,使用LambdaAPI
不再使用LongEventFactory 改用LongEvent::new

不推荐下面这种写法
ringBuffer.publishEvent((event, sequence) -> event.setValue(bb.getLong(0)));

原因是这是一个capturing lambda, 每一个lamda会产生一个对象来承接bb，这样会产生大量的小对象