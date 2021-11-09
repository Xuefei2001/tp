# SchedUrMods User Guide
SchedUrMods is a **desktop application for NUS Students to manage their NUSMODS timetable and
everyday tasks optimised for usage via a Command Line Interface (CLI).** If you can type fast, 
SchedUrMods can help you manage your daily tasks faster as compared to a traditional GUI application.

Below is the guide on how you can get started using our program to start **"Scheduling Ur Mods!"**.  

## Overview <a id="scrollToHere"></a>
- [1. Quick Start](#1-quick-start)
- [2. Features](#2-features)
    - [2.1 Viewing help: `help`](#21-viewing-help--help)
    - [2.2 Adding your tasks:](#22-adding-your-tasks)
      - [2.2.1 Todo: `todo`](#221-todo-todo)
      - [2.2.2 Deadline: `deadline`](#222-deadline-deadline)
      - [2.2.3 Event: `event`](#223-event-event)
      - [2.2.4 Date Format: `<dateFormat>`](#224-date-format-dateformat)
    - [2.3 Listing your tasks: `list`](#23-listing-your-tasks-list)
      - [2.3.1 Listing your entire task list](#231-listing-your-entire-task-list)
      - [2.3.2 Filtering your task list](#232-filtering-your-task-list)
      - [2.3.3 Listing the recurrence of a task](#233-listing-the-recurrence-of-a-task)
      - [2.3.4 Listing your upcoming tasks: `upcoming`](#234-listing-your-upcoming-tasks-upcoming)
    - [2.4 Sorting your task list: `sort`](#24-sorting-your-task-list-sort)
    - [2.5 Editing your tasks: `edit`](#25-editing-your-tasks-edit)
    - [2.6 Adding your NUS timetable:](#26-adding-your-nus-timetable)
      - [2.6.1 Module: `module`](#261-module-module)
      - [2.6.2 Browse: `browse`](#262-browse-browse)
    - [2.7 Deleting your tasks: `delete`](#27-deleting-your-tasks-delete)
    - [2.8 Exiting the program: `bye`](#28-exiting-the-program-bye)
    - [2.9 Storage](#29-storage)
- [3. FAQ](#3-faq)
- [4. Command Summary](#4-command-summary)

## 1. Quick Start
1. Ensure you have [Java **11** jdk](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) installed in your Computer, 
and that `java` is in your `PATH` environment variable.
2. Download the latest jar file from [here](https://github.com/AY2122S1-CS2113T-W13-3/tp/releases/latest).
   > **⚠️Warning**: Do *not* decompress any jar file even if it shows as an archive on your file manager.
3. Move the jar file to the folder you want to use as the _home folder_ for SchedUrMods.
   >💡 **Note**: Ensure you have permission to create files and folders in this _home folder_.
4. Open a terminal window in the _home folder_ you created in step 3.
5. Type `java -jar SchedUrMods.jar` into the terminal to start SchedUrMods.
6. The following display should appear in a few seconds:

```
 _____        _                _  _   _       ___  ___            _
/  ___|      | |              | || | | |      |  \/  |           | |
\ `--.   ___ | |__    ___   __| || | | | _ __ | .  . |  ___    __| | ___
`--.  \ / __|| '_ \  / _ \ / _` || | | || '_| | |\/| | / _ \  / _` |/ __|
/\__/ /| (__ | | | ||  __/| (_| || |_| || |   | |  | || (_) || (_| |\__ \
\____/  \___||_| |_| \___| \__,_| \___/ |_|   \_|  |_/ \___/  \__,_||___/
-------------------------------------------------------------------------
Command-Line Interface for NUSMODS                               (v2.1.0)
-------------------------------------------------------------------------
[user]:
```

7. You may now enter commands into SchedUrMods. Type a command beside
`[user]:` (as shown above) and press `Enter` on your keyboard to execute the command.
8. Refer to the [Features](#2-features) below for details of each command.

## 2. Features
### 2.1 Viewing help : `help`
Displays all available commands for the SchedUrMods application.

**Format**: `help`

### 2.2 Adding your tasks:

> #### 📝 **Some notes when adding tasks**:
> - All commands are case-insensitive. For example, `todo` is the same as `Todo` or `TODO`.
> - Command arguments within `< >` are **mandatory** fields and must be followed strictly.
> - Command arguments within `[ ]` are **optional** fields and may be arranged in any order or left out.
> - Command arguments such as `<a|b|c>` Means that the argument must **strictly** either be `a`, `b` or `c`. 
> - If `[--priority <high|medium|low>]` flag is not used, the **default priority** will be set to `medium`. 
> - If `[--recur <none|daily|weekly|monthly|yearly>]` flag is not used, the **default recurrence** will be set to `none`. 

### 2.2.1 Todo: `todo`
Adds your **todo** to your task list.

**Format**: `todo <description> [--flag <argument>]`
- `<description>` specifies the description of your todo.
- `[--flag <argument>]` specifies optional details of your todo.
  - When your task is to be done: <code>[--doOn <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a>]</code>.
     >💡 **Note**: if flag is unused, date will be set to the current time rounded up to the nearest hour.
  - The priority of your task: `[--priority high|medium|low]`.
  - How often your task is to repeat: `[--recur daily|weekly|monthly|yearly]`.

**Example**: `todo read book --doOn 20-10-2021 02:00 --recur daily`
- Adds a todo task with the description '`read book`' to your task list.
- Specifies that the priority of the task is '`medium`' (default priority).
- Specifies that the task is to be done on '`20-10-2021 02:00`'.
- Specifies that the task is to repeat '`daily`'.

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: todo read book --doOn 20-10-2021 02:00 --recur daily
|| Task created!
|| [T] read book <medium> {daily} (doOn: 20-10-2021 02:00)
-------------------------------------------------------------------------
```

### 2.2.2 Deadline: `deadline`
Adds your **deadline** to your task list.

**Format**: `deadline <description> <--due dd/MM/yyyy HH:mm> [--flag <argument>]`
- `<description>` specifies the description of your deadline.
- <code>&lt;--due <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a>&gt;</code> specifies when your deadline is due.
- `[--flag <argument>]` specifies optional details of your deadline.
  - The priority of your task: `[--priority <high|medium|low>]`.
  - How often your task is to repeat: `[--recur <none|daily|weekly|monthly|yearly>]`.

**Example**: `deadline CS2106 Lab 3 --priority high --due 20-10-2021 02:00`
- Adds a deadline task with the description '`CS2106 Lab 3`' to your task list.
- Specifies that the priority of the task is '`high`'.
- Specifies that the task is to be done on '`20-10-2021 02:00`'.
- Specifies that the task will have no recurrence (default recurrence).

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: deadline CS2106 Lab 3 --priority high --due 20-10-2021 02:00
|| Task created!
|| [D] CS2106 Lab 3 <high> {none} (dueDate: 20-10-2021 02:00)
-------------------------------------------------------------------------
```

### 2.2.3 Event: `event`
Adds your **event** to your task list.

**Format**: `event <description> <--start dd/MM/yyyy HH:mm> <--end dd/MM/yyyy HH:mm> [--flag <argument>]`
- `<description>` specifies the description of your event.
- <code>&lt;--start <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a>&gt;</code> specifies when your event starts.
- <code>&lt;--end <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a>&gt;</code> specifies when your event ends.
- `[--flag <argument>]` specifies optional details of your event.
  - The priority of your task: `[--priority <high|medium|low>]`.
  - How often your task is to repeat: `[--recur <none|daily|weekly|monthly|yearly>]`.

**Example**: `event Marquee Christmas Party --priority high --start 25-12-2020 22:00 --end 26-12-2020 04:00`
- Adds an event task with the description '`Marquee Christmas Party`' to your task list.
- Specifies that the priority of the task is '`high`'.
- Specifies that the task starts on '`25-12-2020 22:00`' and ends on '`26-12-2020 04:00`'.
- Specifies that the task will have no recurrence (default recurrence).

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: event Marquee Christmas Party --priority high --start 25-12-2020 22:00 --end 26-12-2020 04:00
|| Task created!
|| [E] Marquee Christmas Party <high> {none} (startDate: 25-12-2020 22:00 - endDate: 26-12-2020 04:00)
-------------------------------------------------------------------------
```

### 2.2.4 Date Format: `<dateFormat>`

When entering dates into SchedUrMods, we accept multiple date formats to allow you to reduce how much you have to type.  

The accepted date formats in SchedUrMods are:
- `dd-MM-yyyy HH:mm` or `dd-MM-yy HH:mm`
- `dd-MM-yyyy HH` or `dd-MM-yy HH`
- `dd-MM HH:mm`
- `dd-MM HH`
- `dd HH:mm`
- `dd HH`
- `HH`  

**Legend**:
- `dd` represents the **day** the of month.
- `MM` represents the **month** the of year.
- `yyyy` represents the **year**.
- `yy` represents the **year** (in the 21st century)
    -  e.g. `04` represents 2004.
- `HH` represents the **hour** of the day on a 24-hour clock.
- `mm` represents the **minute** of the hour.

>💡 **Note**: When day (`dd`), month (`MM`) or year (`yyyy` or `yy`) is unspecified, they will be replaced with the **current** day, month and year respectively.  

>💡 **Note**: When minute (`mm`) is unspecified, it will be replaced with `0`.

**Examples**:
- `03-10-2004 03` translates to `3rd October 2004, 3:00am`.
- `15:30` translates to `Your current year, month and day at 3.30pm`.
- `15 12:30` translates to `Your current year and month on the 15th at 12.30pm`.

### 2.3 Listing your tasks: `list`

There are **3 main features** you can use with the list command.

#### 2.3.1 Listing your entire task list
List all tasks currently stored locally in your task list.

**Format**: `list`
- The command displays the following information for each task:
  - **Task index**:
    - A positive integer i.e. `1.` which identifies the task. This index is used to identify tasks in the [`edit`](#25-editing-your-tasks-edit), [`browse`](#262-browse-browse) and [`delete`](#27-deleting-your-tasks-delete) commands.
  - **Task type**:
    - A task can either be a Todo, Deadline, Event or Lesson and they are all represented with the first letter of their task type.
    - `[T]` represents a `Todo`.
    - `[D]` represents a `Deadline`.
    - `[E]` represents a `Event`.
    - `[L]` represents a `Lesson`.
  - **Task description**:
    - General details of the task.
  - **Task priority `[priority]`**:
    - A task can either have `low`, `medium`, or `high` priority. 
    - This value determines the urgency of the task.
  - **Task recurrence `{recurrence}`**:
    - A task can either have `none`, `daily`, `weekly`, `monthly`, or `yearly` recurrence.
    - This value determines how often a task recurs.
        >💡 **Note**: Tasks with a recurrence will automatically be recurred to the next date that has not occured yet every time you list them.
  - **Task date field**:
    - For a `Todo`, it represents the date and time to do the task.
    - For a `Deadline`, it represents the due date of the task.
    - For an `Event`, it represents the start date and end date of the event.
    - For a `Lesson`, it represents the date and duration of the lesson.

>💡 **Note**: For `Lesson` tasks, the task date field is combined with the task description.

**Example**: `list`
- List all tasks that are in your task list.

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: list
|| -------------
||  MY TASK LIST
|| -------------
|| 1. [T] read book <low> {daily} (doOn: 05-11-2021 02:00)
|| 2. [D] return book <medium> {weekly} (dueDate: 11-11-2021 03:00)
|| 3. [E] project meeting <high> {monthly} (startDate: 22-11-2021 04:00 - endDate: 22-11-2021 05:00)
|| 4. [T] exercise <medium> {none} (doOn: 04-11-2021 18:00)
|| 5. [T] wash clothes <medium> {weekly} (doOn: 10-11-2021 02:00)
|| 6. [D] project submission <high> {monthly} (dueDate: 21-11-2021 03:00)
|| 7. [E] movie screening <low> {daily} (startDate: 05-11-2021 04:00 - endDate: 05-11-2021 05:00)
-------------------------------------------------------------------------
```

#### 2.3.2 Filtering your task list
Filters the task list for all tasks that matches the filters applied.

**Format**: `list [--filter <argument>] [--filter <argument>] ...`
- `[--flag <argument>]` specifies the filter to be applied to your task list.
  - Type of the task: `[--type <todo|deadline|event|lesson>]`.
  - Priority of the task: `[--priority <low|medium|high>]`.
  - Recurrence of the task: `[--recur <none|daily|weekly|monthly|yearly>]`.

**Example**: `list --type todo --priority medium`
- List all tasks that are of type `Todo` and has a priority of `medium`.

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: list --type todo --priority medium
|| -------------
||  MY TASK LIST
|| -------------
|| 1. [T] exercise <medium> {none} (doOn: 04-11-2021 18:00)
|| 2. [T] wash clothes <medium> {weekly} (doOn: 10-11-2021 02:00)
-------------------------------------------------------------------------
```

#### 2.3.3 Listing the recurrence of a task
Displays the next four recurrences of a task.

**Format 2**: `list [id]`
- `[id]` specifies the id of the task to display the next four recurrences of.
  >💡 **Note**: To obtain the correct task id of each task, please use the `list` command without any filters.

**Example (with task id)**: `list 3`
- Lists the next four recurrences of the task with the index `3`.

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: list 3
|| -------------
||  MY TASK LIST
|| -------------
|| Listing next 4 recurrences for:
|| [E] project meeting <high> {monthly} (startDate: 22-11-2021 04:00 - endDate: 22-11-2021 05:00)
|| -> 22-12-2021 04:00
|| -> 22-01-2022 04:00
|| -> 22-02-2022 04:00
|| -> 22-03-2022 04:00
-------------------------------------------------------------------------
```

#### 2.3.4 Listing your upcoming tasks: `upcoming`
Allows you to easily list your tasks over the upcoming week.

**Format**: `upcoming`

**Example**: `upcoming`

**Expected Outcome**:
```
-------------------------------------------------------------------------
[user]: upcoming
|| [L] CS2113T C02: 10-11-2021 13:00 to 10-11-2021 14:00 <medium> {weekly} 
|| [T] buy groceries <medium> {none} (doOn: 10-11-2021 17:30)
|| [L] CS2113T C02: 11-11-2021 14:00 to 11-11-2021 16:00 <medium> {weekly} 
|| [L] CS2113T C02: 12-11-2021 16:00 to 12-11-2021 18:00 <medium> {weekly} 
|| [L] CS2113T C02: 15-11-2021 14:00 to 15-11-2021 16:00 <medium> {weekly} 
|| [E] finals <medium> {none} (startDate: 15-11-2021 15:00 - endDate: 15-11-2021 18:00)
-------------------------------------------------------------------------
```


### 2.4 Sorting your task list: `sort`
Sorts your task list by a given criteria.

**Format**: `sort --by <criteria>`
- `<criteria>` specifies what to sort your task list by.
  - Type of the task: `type`.
  - Description of the task: `description`.
  - Priority of the task: `priority`.

**Example**: `sort --by priority` + `list`
- Sort your current task list by `priority` from `high` to `low`.
- Print out the sorted list with the `list` command.

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: sort --by priority
|| [!] Task list has been sorted by: priority
-------------------------------------------------------------------------
[user]: list
|| -------------
||  MY TASK LIST
|| -------------
|| 1. [E] project meeting <high> {monthly} (startDate: 22-11-2021 04:00 - endDate: 22-11-2021 05:00)
|| 2. [D] project submission <high> {monthly} (dueDate: 21-11-2021 03:00)
|| 3. [D] return book <medium> {weekly} (dueDate: 11-11-2021 03:00)
|| 4. [T] exercise <medium> {none} (doOn: 04-11-2021 18:00)
|| 5. [T] wash clothes <medium> {weekly} (doOn: 10-11-2021 02:00)
|| 6. [T] read book <low> {daily} (doOn: 05-11-2021 02:00)
|| 7. [E] movie screening <low> {daily} (startDate: 05-11-2021 04:00 - endDate: 05-11-2021 05:00)
-------------------------------------------------------------------------
```

### 2.5 Editing your tasks: `edit`
Edits a single [Todo](#221-todo-todo), [Deadline](#222-deadline-deadline) or [Event](#223-event-event) based on the flags entered.
**Format**: `edit <index> <--flag <value>> [--flag <value>] [--flag <value>]...`
- `<index>` is the index of the task you want to edit from the most recent [list](#23-listing-your-tasks-list) command that you have run.
   >💡 **Note**: if the task you want to edit was not in your most recent [list](#23-listing-your-tasks-list) command, simply type `list` to list all tasks and use its corresponding index to edit it.
- at least one `<--flag <value>>` must be specified, though you can edit multiple values at once by providing multiple more `[--flag <value>]` arguments.
- **Flags**
  - `--description <description>` modifies your task description.
  - `--priority <high|medium|low>` modifie yours task priority.
  - `--recur <none|daily|weekly|monthly|yearly>` modifies your task recurrence.
  - <code>--doOn <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a></code> modifies when your [Todo](#221-todo-todo) is to be done.
  - <code>--due <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a></code> modifies when your [Deadline](#222-deadline-deadline) is due.
  - <code>--start <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a></code> modifies when your [Event](#223-event-event) starts.
  - <code>--end <a href="#224-date-format-dateformat">&lt;dateFormat&gt;</a></code> modifies when your [Event](#223-event-event) ends.
  - `--link <url>` modifies your Lesson link.


### 2.6 Adding your NUS timetable:

Allows you to add modules into your task list and browse to their meeting links with your Operating System's default internet browser.

#### 2.6.1 Module: `module`

Adds your modules and classes into your task list.

**Format**: `module {<module code>:{<class number>,}...;}...`

- `<module code>` is the official module code of a module. E.g. `CS2113T`.
- `<class number>` is the official class group number. E.g. `C02`.

**Example**: `module CS2113T:C02;LAJ2203:1,A2,B4`

- Adds class '`C02`' of the module '`CS2113T`' and classes '`1`', '`A2`', and '`B4`' of the module '`LAJ2203`' into your task list.

**Expected outcome**:

```
-------------------------------------------------------------------------
[user]: module CS2113T:C02;LAJ2203:1,A2,B4
|| Added CS2113T C02
|| Added LAJ2203 1
|| Added LAJ2203 A2
|| Added LAJ2203 B4
-------------------------------------------------------------------------
```

#### 2.6.2 Browse: `browse`

Browse to links that were added to your `Lesson` with the [`edit`](#25-editing-your-tasks-edit) command.
**Format**: `browse <index>`
- `<index>` is the index of the task you want to browse to (open in an internet browser) in the most recent [list](#23-listing-your-tasks-list) command that you have run.
  >💡 **Note**: if the task you want to browse to was not in your most recent [list](#23-listing-your-tasks-list) command, simply type `list` to list all your tasks and use its corresponding index to browse to the task's url.

**Example**: `browse 2`

**Expected outcome**:
```
-------------------------------------------------------------------------
[user]: browse 2
|| https://google.com
-------------------------------------------------------------------------
```

>💡 **Note**: The opening of the link in your default browser is done by the operating system. As such, the actual protocols supported is
> dependent on your OS.


>💡 **Note**: There may be error messages printed on your terminal which can be from the associated application
> used in opening the link, such as Chrome, and not SchedUrMods.

>💡 **Note**: If there is no link in the lesson being specified, you will be prompted with an error:
>
> ```
> -------------------------------------------------------------------------
> [user]: browse 2
> || There is no link associated with the requested task.
> -------------------------------------------------------------------------
> ```
> You can conveniently list the tasks and see if the lesson has a link.

### 2.7 Deleting your tasks: `delete`
Deletes all task(s) specified in a comma-separated argument.
**Format**: `delete <indexes>`
- `<indexes>` are the indexes of the task you want to edit from the most recent [list](#23-listing-your-tasks-list) command that you have run.
   >💡 **Note**: if the task(s) you want to delete was not in your most recent [list](#23-listing-your-tasks-list) command, simply type `list` to list the tasks and use their indexes to delete them.
  - Indexes should be comma separated single integers such as `5` and/or ranges of two indexes such as `1-3`.
  - **Example**:
   - `delete 1,3,5` would delete tasks with indexes 1, 3 and 5.
   - `delete 1-3` would delete tasks with indexes 1, 2 and 3.
   - `delete 1-3, 5` would delete tasks with indexes 1, 2, 3 and 5.
   - `delete 5` would delete the task with the index 5.
      >💡 **Note**: It does not matter if there are any spaces in-between the commas.


**Example**: `delete 1-3, 5`
- delete the tasks with indexes 1, 2, 3 and 5.

**List before deletion**:

```
-------------------------------------------------------------------------
[user]: list
|| -------------
||  MY TASK LIST
|| -------------
|| 1. [T] read book <low> {none} (doOn: 20-10-2021 02:00)
|| 2. [T] return book <low> {none} (doOn: 21-10-2021 03:00)
|| 3. [E] project meeting <high> {none} (startDate: 22-10-2021 04:00 - endDate: 22-10-2021 05:00)
|| 4. [T] wash clothes <medium> {none} (doOn: 20-10-2021 02:00)
|| 5. [D] homework <low> {none} (dueDate: 21-10-2021 03:00)
|| 6. [E] movie screening <low> {none} (startDate: 22-10-2021 04:00 - endDate: 22-10-2021 05:00)
-------------------------------------------------------------------------
```
**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: delete 1-3,5
|| Task(s) deleted:
|| [T] read book <low> {none} (doOn: 20-10-2021 02:00)
|| [T] return book <low> {none} (doOn: 21-10-2021 03:00)
|| [E] project meeting <high> {none} (startDate: 22-10-2021 04:00 - endDate: 22-10-2021 05:00)
|| [D] homework <low> {none} (dueDate: 21-10-2021 03:00)
-------------------------------------------------------------------------
```

**List after deletion**:

```
-------------------------------------------------------------------------
[user]: list
|| -------------
||  MY TASK LIST
|| -------------
|| 1. [T] wash clothes <medium> {none} (doOn: 20-10-2021 02:00)
|| 2. [E] movie screening <low> {none} (startDate: 22-10-2021 04:00 - endDate: 22-10-2021 05:00)
-------------------------------------------------------------------------
```

### 2.8 Exiting the program: `bye`
Displays goodbye message and exits the program.

**Format**: `bye`

**Expected Outcome**:

```
-------------------------------------------------------------------------
[user]: bye
|| Exiting program!
-------------------------------------------------------------------------
```

### 2.9 Storage
The program will store and automatically update your tasks in the file `[project directory]/data/task.dat` on any change to your task list.  
   > **⚠️Warning**: The SchedUrMods team **strongly discourages** users from directly modifying Task data in `task.dat` as it can lead to undefined behaviour. The team **will not be responsible** for any *loss of data* or *undefined behaviour* as a result of users directly modifying values in the save file.


## 3. FAQ
**Q**: What operating systems can the application work on?

**A**: The SchedUrMods application supports all Windows, Linux and Unix operating systems 
that have the `java 11 jdk` installed on their systems. 

**Q**: How do I transfer my data to another computer?

**A**: Install the program on the other computer and replace the new empty `tasks.dat`
it creates with the `tasks.dat` that you wish to transfer from your previous `SchedUrMods.jar`
home folder.

**Q**: I encountered an error message `java.lang.reflect.InaccessibleObjectException` when executing the program. How do I fix it?

**A**: Ensure that you have installed and are running `java jdk 11`. Check your java version, by typing `java -version`
to ensure that the correct jdk version (`11.x.x`) is being recognised by your system.

## 4. Command Summary

Action   | Format                                           | Example                                                                                              |
-------- | -------------------------------------------------| -----------------------------------------------------------------------------------------------------|
help     | `help`                                           | `help`                                                                                               |
todo     | `todo <description> [--flag <argument>]`         | `todo read book`                                                                                     |
deadline | `deadline <description> [--flag <argument>]`     |`deadline CS2106 Lab 3 --priority high --due 20-10-2021 02:00`                                        |
event    | `event <description> [--flag <argument>]`        | `event Marquee Christmas Party --priority high --start 25-12-2020 22:00:00 --end 26-12-2020 04:00`   |
list     | `list [--filter <argument>]` or `list [task id]` | `list` or `list --type todo --priority medium` or `list 3`                                           |
upcoming | `upcoming`                                       | `upcoming`                                                                                           |
sort     | `sort --by <criteria>`                           | `sort --by priority`                                                                                 |
edit     | `edit <index of task to delete>`                 | `edit 3`                                                                                             |
module   | `module {<module code>:{<class number>,}...;}...`| `module CS2113T:C02;LAJ2203:1,A2,B4`                                                                 | 
browse   | `browse <index>`                                 | `browse 2`                                                                                           |
delete   | `delete <index(es) of task(s) to delete>`        | `delete 1, 2, 4-7`                                                                                   |
bye      | `bye`                                            | `bye`                                                                                                |
