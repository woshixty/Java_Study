10-2
* IoSelectorProvider Selector、Channel、SelectorKey之间的并发锁死等待BUG
* IoSelectorProvider Selector、Channel、SelectorKey已关闭的异常捕获

10-3
* 关闭链接时关闭Packet没有重新设置node导致的线程无限循环的问题
* 关闭链接时可能出现Writer中的packerMap被清空导致的空指针异常问题
* 基础的键盘IO输入在强制关闭时可能触发控空指针异常

10-4
