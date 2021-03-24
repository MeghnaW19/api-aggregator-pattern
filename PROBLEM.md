## Problem Statement: Implementing API Aggregator pattern

Use a gateway to aggregate multiple individual requests into a single request. This pattern is useful when a client must make multiple calls to different backend systems to perform an operation.


Create 3 microservices individually :
 - api-gateway-service
	- Contains a controller that posts a blog for validation and formatting
	- The controller uses rest template to post the blog  for validation first.
	- If it contains informal words ,then a maessge"Invalid content" is returned to the client
	- If the content is  valid,then the blog is posted for formatting using Rest Template.
	- Finally the aggregated respose of the formatted blog is sent back to the client.	
 - content-filter-service
	- Contains a controller,a service layer and a dto layer
	- If the Content in the blog contains the following words,then it is deemed as Invalid content.Set the status message and count of 		   informal words accordingly.
		hi,hey,hello,dude,babes,rocks				

 - content-formatting-service
	- Contains a controller,a service layer and a dto layer
	- Formats the content to upper case

Complete the class BlogDto

**class BlogDto**

Define the following properties. properties should be private:

        -blogId               : int 
        -title                : String 
        -content 	          : String
	    -author 	          : String
	    -statusMessage        : String
	    -countOfInformalWords : int

- Define parameterized constructor to initialize all the properties. 

- Define Getters and Setters for all properties 
     

## Instructions
- Do not change the provided class/method names unless instructed
- Ensure your code compiles without any errors/warning/deprecations 
- Follow best practices while coding
