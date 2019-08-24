同步容器类

1：Vector Hashtable ：早期使用synchronized实现 
2：ArrayList HashSet ：未考虑多线程安全（未实现同步）
3：HashSet vs Hashtable StringBuilder vs StringBuffer
4：Collections.synchronized***工厂方法使用的也是synchronized

使用早期的同步容器以及Collections.synchronized***方法的不足之处，请阅读：
http://blog.csdn.net/itm_hadf/article/details/7506529

使用新的并发容器
http://xuganggogo.iteye.com/blog/321630

5：