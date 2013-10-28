package myAspect;

public aspect Error {
    pointcut ExceptionHandlerPointout(Exception exception) : handler(Exception) && args(exception);
    
    //异常对象初始化 Advice
    before(Exception exception) : ExceptionHandlerPointout(exception) {
    	String content = String.format("[异常信息] 源文件：%s   方法名：%s   细节：%s\r\n", thisJoinPoint.getSourceLocation(),thisJoinPoint.getStaticPart(),exception.toString());
    	FileLogger.logError(content);
    }

}
