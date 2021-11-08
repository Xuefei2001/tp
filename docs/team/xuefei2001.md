# Hu Xuefei - Project Portfolio Page

## Product Overview <a id="scrollToHere"></a>    

**SchedUrMods** is a desktop application for NUS students who wish to manage their assignments
and semester-related information via CLI (command-line interface). If you can type fast, SchedUrMods
can help you manage your daily tasks faster than traditional GUI application.

Given below are the contributions I have made to this project.

### Summary of Contributions    

- **New Feature**: Add the pop-up reminder for tasks that have time constraint.
  - **Improvement 1**: Develop and Implement `Reminder.java` class
    - What it does: By default, display a pop-up reminder 10 min before a task with time constraint or a lesson starts or need to be finished.
    It can also update its reminder time with recurrence, so there is a reminder for every task that has a time constraint, regardless of priority, every time a task is about to happen or end.
    - Justification: This feature can largely improve user experience as it broaden the ability of the product. 
    With the reminder feature, SchedUrMods is no longer merely a todo-list, it also has similar function as an alarm. 
    Users can not only use it to keep track of their tasks, but also be **reminded** not to miss any important task. 
    With the automatically popped-up reminder, users do not need to constantly check the list to find out which task is due soon.
  - **Improvemwnt 2**: Implement `ReminderManager.java` class
    - Function 1: check the whole task list to see if there is a reminder
      to be displayed
      - What it does: Once the method is called to check for reminder, it can check through the whole task list against the current system time to see if any reminder needs to be sent.
      It can also consolidate the reminder message when there should be reminders for multiple tasks and send out as one pop-up message instead of send different reminder one-by-one.
      - Justification: In this way,comparing to printing out each reminder one by one, the buffer time needed for the system time to match the reminder time is shortened,
      the program the can run more efficiently. 
    - Function 2: customization of the reminder
      - What it does: Together with the implementation of `ReminderCommand`, `ReminderManager` can also change the setting of time and message for each reminder.
      The time a reminder pop up is no longer fixed at 10 minutes, it can be changed to a few days or a few minutes and the message can also be customized by users.
      - Justification: This improve the product by bringing in customization by user, in this way, users can have an opportunity to choose 
      when they want to be reminded what message they want to see when the reminder popps up, and the setting can be different for different task. 
      Customization is normally preferred and hence can help us to attract more users.
    

- **Code Contributed**: [RepoSense Link](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=Xuefei&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25)
    

- **Documentation**:
  - User Guide:
  Added documentation for the features `reminder` under [section 2.5](https://ay2122s1-cs2113t-w13-3.github.io/tp/UserGuide.html#25-setting-reminders-for-your-tasks-reminder)
  - Developer Guide:
  Added implementation and UML diagrams for [Reminder Component](https://ay2122s1-cs2113t-w13-3.github.io/tp/DeveloperGuide.html#36-reminder-component)
