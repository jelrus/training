## Branching Task

----------------------------------------------------------------

### Contents

1. Task
2. Used commands
3. Solution

----------------------------------------------------------------

### 1. Task

Learn basic git commands by recreating steps from tutorial applied to task

----------------------------------------------------------------

### 2. Used commands

- Console commands

| Syntax             | Description                                                                    |
|--------------------|--------------------------------------------------------------------------------|
| `mkdir <dirname>`  | creates new directory in current directory with `<dirname>` if not exist       |
| `cd <path>`        | changes current working directory to directory with `<path>`                   |
| `ls -1`            | shows contents of the directory<br/> flag `-1` shows contents in vertical list |
| `touch <filename>` | creates empty file with `<filename>`                                           |

- Git commands

| Syntax                                                                        | Description                                                                                                                         |
|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| `git init`                                                                    | creates empty git repository in working directory                                                                                   |
| `git status`                                                                  | displays the tracked, untracked files and changes                                                                                   |
| `vim <filename>`                                                              | launches file in vim editor                                                                                                         |
| `git add <path>`                                                              | adds file contents to index                                                                                                         |
| `git commit -m "<msg>"`                                                       | records changes to repository<br/> flag `-m <msg>` used to add given `<msg>` <br/>as the commit message                             |
| `git checkout <branchname>`<br/>`git checkout -b <branchname>`                | switches to branch with `<branchname>`<br/>flag `-b <branchname` used to create new branch <br/> in current branch and switch to it |
| `git show-branch`                                                             | shows branches and their commits                                                                                                    |
| `vi <filename>`                                                               | launches file in vi editor                                                                                                          |
| `git diff --cached`                                                           | shows changes between commits<br/> flag `--cached` use to see changes between latest and staged commits                             |
| `git merge <branchname> --no-ff`                                              | joins two or more development histories together<br/>flag `--no-ff` used to merge commit in all cases                               |
| `gitk`                                                                        | launches graphical repository browser                                                                                               |
| `git log --all --graph -- decorate` <br/>`--oneline --simplify-by-decoration` | shows commit log                                                                                                                    |
| `git log --graph --oneline --all`                                             | shows commit log                                                                                                                    |


----------------------------------------------------------------

### 3. Solution

1.  Create new directory `branching-task-repo` for repo with `mkdir branching-task-repo`

![](media/01.png)

2. Change directory to created with `cd branching-task-repo/`

![](media/02.png)

3. Create empty git repository with `git init`

![](media/03.png)

4. Check untracked changes with `git status`

![](media/04.png)

5. Check if repository empty with `ls -1`

![](media/05.png)

6. Create empty *.md file README.md with `touch README.md`

![](media/06.png)

7. Check untracked changes with `git status` <br> As we can see repo have untracked upper-mentioned `README.md` file

![](media/07.png)

8. Open `README.md` in vim with `vim README.md`

![](media/08.png)

9. In opened window press `i` to enter edit mode. <br> Add text e.g. `Hello World!` to the `README.md` on the first line. <br> Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/09.png)

10. Check untracked changes with `git status`<br> As we can see file `README.md` is still untracked.

![](media/10.png)

11. Add current directory to staging state with `git add .`

![](media/11.png)

12. Check tracked changes with `git status`<br> As we can see file `README.md` is ready to be committed (staged)

![](media/12.png)

13. Commit `README.md` to `master` branch with message `created readme` with `git commit -m "created readme"`

![](media/13.png)

14. Check if commit applied properly with `git status`<br>As we can see `README.md` was committed and there's nothing to commit

![](media/14.png)

15. Create new branch `develop` from `master` branch and switch to it with `git checkout -b develop`

![](media/15.png)

16. Check if branch `develop` was created with `git show-branch`

![](media/16.png)

17. Create new branch `git_task` from `develop` branch and switch to it with `git checkout -b git_task`

![](media/17.png)

18. Check if branch `git_task` was created with `git show-branch`

![](media/18.png)

19. Create new branch `git_0` from `git_task` branch and switch to it with `git checkout -b git_0`

![](media/19.png)

20. Check if branch `git_0` was created with `git show-branch`

![](media/20.png)

21. Open `README.md` in vi with `vi README.md`

![](media/21.png)

22. In opened window press `i` to enter edit mode. <br> Edit text e.g. add new line to text. <br> Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/22.png)

23. Check untracked changes with `git status`.<br>As we can see file `README.md` was modified.

![](media/23.png)

24. Add current directory to staging state with `git add .`

![](media/24.png)

25. Check tracked changes with `git status`.<br>As we can see file `README.md` is ready to be committed (staged)

![](media/25.png)

26. Look on the differences in `README.md` file with `git diff --cached`

![](media/26.png)

27. Commit `README.md` file to `git_0` branch with message `added git_0 line to readme` with `git commit -m "added git_0 line to readme"`

![](media/27.png)

28. Check if file `README.md` was committed properly.<br>As we can see `README.md` was committed and there's nothing to commit

![](media/28.png)

29. Switch to `git_task` branch `git checkout git_task`

![](media/29.png)

30. Merge `git_0` branch with `git_task` branch with `git merge git_0 --no-ff`

![](media/30.png)

31. Check changes in branches with `git show-branch`

![](media/31.png)

32. Enter gitk with `gitk`

![](media/32.png)

33. Look on the changes in `gitk` and exit window

![](media/33.png)

34. Check view mode with `git log --all --graph --decorate --oneline --simplify-by-decoration`

![](media/34.png)

35. Check view mode with `git log --graph --oneline --all`

![](media/35.png)

36. Switch to `develop` branch with `git checkout develop`

![](media/36.png)

37. Merge `git_task` branch with `develop` branch with `git merge git_task --no-ff`

![](media/37.png)

38. Check applied changes in log with `git log --all --graph --decorate --oneline --simplify-by-decoration`

![](media/38.png)

39. Check applied changes in log with `git log --graph --oneline --all`

![](media/39.png)

40. Switch to `master` branch with `git checkout master`

![](media/40.png)

41. Merge `develop` branch with `master` branch with `git merge develop --no-ff`

![](media/41.png)

42. Check applied changes in log with `git log --all --graph --decorate --oneline --simplify-by-decoration`

![](media/42.png)

43. Check applied changes in log with `git log --graph --oneline --all`

![](media/43.png)