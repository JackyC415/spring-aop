package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class ValidationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public void validateTweet(JoinPoint joinPoint) {
		System.out.printf("Validation check before the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		String user = (String) joinPoint.getArgs()[0];
		String msg = (String) joinPoint.getArgs()[1];
		
		if(msg.length() > 140 || msg == null || user == null || msg.isEmpty() || user.isEmpty()) {
			throw new IllegalArgumentException("Invalid tweet input!");
		}
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void validateFollower(JoinPoint joinPoint) {
		System.out.printf("Validation check before the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		
		if(follower == null || followee == null || follower.isEmpty() || followee.isEmpty()) {
			throw new IllegalArgumentException("follower or followee cannot be empty or null!");
		} else if (follower.equals(followee)) {
			throw new UnsupportedOperationException("user cannot follow themselves!");
		}
		
	}
	
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void validateBlock(JoinPoint joinPoint) {
		System.out.printf("Validation check before the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		String user = (String) joinPoint.getArgs()[0];
		String follower = (String) joinPoint.getArgs()[1];
		
		if(user == null || follower == null || user.isEmpty() || follower.isEmpty()) {
			throw new IllegalArgumentException("user or follower cannot be empty or null!");
		} else if (user.equals(follower)) {
			throw new UnsupportedOperationException("user cannot block themselves!");
		}
		
	}
	
	
	
}
