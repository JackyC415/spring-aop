package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */
	
	private final static int maximumRetries = 3;
	
	@Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public void retryNetworkFailure(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Integer result = null;
		
		System.out.printf("Prior to the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		
		try {
			joinPoint.proceed();
		} catch(IOException e1) {
			e1.printStackTrace();
			System.out.println("Retrying network failure first attempt...");
			try {
				joinPoint.proceed();
			} catch(IOException e2) {
				e2.printStackTrace();
				System.out.println("Retrying network failure second attempt...");
				try {
					joinPoint.proceed();
				} catch (IOException e3) {
					e3.printStackTrace();
					System.out.println("Retrying network failure final attempt...");
					try {
						joinPoint.proceed();
					} catch (IOException e4) {
						e4.printStackTrace();
						System.out.println("Aborted, network failure!");
						throw e4;
					}
				}
			}
		}
		
		
		int attempt = 0;
		while(attempt <= maximumRetries) {
			try {
				joinPoint.proceed();
				System.out.printf("Finished the executuion of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
			} catch(IOException e) {
				if(attempt >= 3) {
					throw e;
				}
				e.printStackTrace();
				System.out.println("Retrying network failure attempt: " + attempt);
				attempt++;
			}
		}
		
		
		/*
		for(int attempt = 1; attempt <= maximumRetries; attempt++) {
			try {
				result = (Integer) joinPoint.proceed();
				if(result > 0) {
					System.out.printf("Finished the executuion of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
					break;
				}
			} catch (IOException e) {
				if(attempt == 3) {
					System.out.printf("Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
					throw e;
				}
				e.printStackTrace();
				System.out.println("Retrying network failure attempt: " + attempt);
				continue;
			}
		}*/
		
	}

}
