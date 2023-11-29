# Course Planning

## Phase 0

### Questions and Answers
- What will the application do?   
We can add courses to **my course list**.  
We can register courses in **my course list**.  
We can see total course credits of registered courses in **my course list**.
- Who will use it?  
University students!
- Why is this project of interest to you?  
Because it can help students to check their course planning.  

### User Stories  
- As a user, I want to be able to add courses to **my course list**.
- As a user, I want to be able to view courses in **my course list**.
- As a user, I want to be able to mark a course as registered.
- As a user, I want to be able to remove courses from **my course list**.
- As a user, I want to be able to see total course credits of all registered courses in **my course list**.  
- As a user, I want to be able to save **my course list** to file (if I choose).
- As a user, I want to be able to be able to load **my course list** from file (if I choose)

### Instructions for Grader
- You can generate the first required action related to the user story 
"adding course to course list" by entering course name in the first text box, 
entering course credit in the second text box and clicking add button. 
- You can generate the second required action related to the user story 
"removing course from course list" by selecting the course you want to remove 
and clicking remove button. 
- You can locate my visual component by clicking data file and then clicking image file under data file
and there is an icon.png which is my visual component.
- You can save the state of my application by clicking save button.
- You can reload the state of my application by clicking load button.
 
### Phase 4: Task 2
EventLog:

Wed Nov 29 13:30:16 PST 2023

Load course list from file!

Wed Nov 29 13:30:22 PST 2023

Add <Course name: CPSC121 Course credit: 4 Course status: Unregistered> to the course list!

Wed Nov 29 13:30:25 PST 2023

Remove <Course name: CPSC110 Course credit: 4 Course status: Unregistered> from the course list!

Wed Nov 29 13:30:29 PST 2023

Save course list to file!
### Phase 4: Task 3
1. Refactoring: I will design a NonPositiveCredit exception in the future 
for Course class. Reason: To make constructor: Course() more robust.
2. Refactoring: I will design a Registered exception in the future
   for CourseList class. Reason: To make method: addCourse(), removeCourse() more robust.
3. 
