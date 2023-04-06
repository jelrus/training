## Remote Repo Task

----------------------------------------------------------------

### Contents

1. Task
2. Used commands
3. Solution <br>
3.1. Preparations <br> 3.2. Part 1 <br> 3.3. Part 2

----------------------------------------------------------------

### 1. Task

Practice skills obtained from branching task, master new git commands and manipulations with remote and
local repositories

----------------------------------------------------------------

### 2. Used commands

- Console commands

| Syntax             | Description                                                                     |
|--------------------|---------------------------------------------------------------------------------|
| `mkdir <dirname>`  | creates new directory in current directory with `<dirname>` if not exist        |
| `mkdir -p <path>`  | creates new directories in current directory according to `<path>` if not exist |
| `cd <path>`        | changes current working directory to directory with `<path>`                    |
| `touch <filename>` | creates empty file with `<filename>`                                            |

- Git commands

| Syntax                                                                              | Description                                                                                                      |
|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| `git clone <path>`                                                                  | clones project according to specified `<path>` or link                                                           |
| `vim <filename>`                                                                    | opens file with `<filename>` in vim editor                                                                       |
| `git add <filename>`                                                                | stages file with `<filename>`                                                                                    |
| `git commit -m "<message>"`                                                         | creates commit with specified `<message>`                                                                        |
| `git log --all --oneline`                                                           | shows logs                                                                                                       |
| `git branch -m <branch> <name>`                                                     | renames `<branch>` with specified `<name>`                                                                       |
| `git checkout -b <name>`                                                            | creates branch with specified `<name>` and switches to it                                                        |
| `git checkout <name>`                                                               | switches to branch with specified `<name>`                                                                       |
| `git push --all -u`                                                                 | pushes all branches to remote repository and add for each successfully pushed branch tracking reference          |
| `git merge <branch> --no-ff`                                                        | merges `<branch>` to current branch, merge conflict resolves manually                                            |
| `git format-patch <n> <hash>`                                                       | creates `<n>` patches from the topmost commit specified by `<hash>`                                              |
| `git apply <patch-name>`                                                            | applies patch  specified by `<patch-name`> to current branch                                                     |
| `git add .`                                                                         | stages all untracked files                                                                                       |
| `git cherry-pick --edit <hash>`                                                     | cherry-picks commit specified by `<hash>` to current branch with edit message option                             |
| `git reset HEAD~<n>`                                                                | resets current HEAD to specified `<n>` commit from the topmost                                                   |
| `git commit --amend --no-edit --author "<author>" <br> --date=<date> -m "<message"` | replaces the tip of the current branch by creating a new commit with specified `<author>`, `<date>`, `<message>` |
| `git revert HEAD~n`                                                                 | reverts the changes specified by the `<n>` commit in HEAD, creates a new commit with the reverted changes        |
| `git reset --hard HEAD~n`                                                           | resets current HEAD and working tree to specified `<n>` commit from the topmost                                  |
| `git fetch`                                                                         | downloads objects and refs from another repository                                                               |
| `git merge <remote>/<branch>`                                                       | merges current local branch with `<remote>` `<branch>`                                                           |
| `git push`                                                                          | updates remote refs along with associated objects                                                                |
| `git pull`                                                                          | downloads objects and refs from another repository and merges current local branch with `<remote>` `<branch>`    |
| `git stash`                                                                         | saves current local repo state                                                                                   |
| `git stash pop`                                                                     | returns to local state from the topmost stash                                                                    |
| `git stash list`                                                                    | shows list of stashes                                                                                            |
| `git stash pop@{<id>}`                                                              | returns to local state specified by stash with `<id>`                                                            |
| `git rebase -i HEAD~<n>`                                                            | launches interactive rebase for `<n>` topmost from HEAD commits                                                  |
| `git reflog`                                                                        | shows reference logs                                                                                             |

----------------------------------------------------------------

### 3. Solution

----------------------------------------------------------------

###3.1. Preparations

    First create a new remote repository. Clone it. Create new file named README.md with any text 
    and commit it with "initial commit" message.

    When you have first commit, you're able to create branches. To start this task, you need to create 3 branches:

    1. Start from master. From master create branch named git_task and checkout it.
    2. From git_task you should create two branches: git_1 and git_2.

    Push every branch to the remote (see git push --all -u and read about upstream and tracked branches). At the end 
    you should have 4 branches and README.md file both in your local and remote repositories:

    master, git_task, git_1 and git_2

1. Create repository on gitHub which will be the remote one

![](media/preparations/01.png)

2. Copy clone link. In this example it is `https://github.com/jelrus/remote-repo.git`

![](media/preparations/02.png)

3. Create folder for project clone on disk with `mkdir -p remote-repo-task/first-clone`

![](media/preparations/03.png)

4. Change current directory to newly created with `cd remote-repo-task/first-clone`

![](media/preparations/04.png)

5. Clone project with `git clone https://github.com/jelrus/remote-repo.git`

![](media/preparations/05.png)

6. Change current directory to cloned project directory with `cd remote-repo` 

![](media/preparations/06.png)

7. Create file README.md with `touch README.md`

![](media/preparations/07.png)

8. Open file README.md with `vim README.md`

![](media/preparations/08.png)

9. In opened window press `i` to enter edit mode. <br> 
   Add text e.g. `This is readme for this task` to the `README.md` on the first line. <br> 
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/preparations/09a.png)<br><b>Before editing</b>

![](media/preparations/09b.png)<br><b>After editing</b>

10. Stage file README.md with `git add README.md`

![](media/preparations/10.png)

11. Commit changes for file README.md with `git commit -m "initial commit"`

![](media/preparations/11.png)

12. Check log for changes with `git log --all --oneline`

![](media/preparations/12.png)

<b>Optional. Rename main branch to master with `git branch -m main master`</b>

![](media/preparations/opt.png)

13. Create branch `git_task` with `git checkout -b git_task`

![](media/preparations/13.png)

14. Create branch `git_1` from branch `git_task` with `git checkout -b git_1`

![](media/preparations/14.png)

15. Switch to branch `git_task` with `git checkout git_task`

![](media/preparations/15.png)

16. Create branch `git_2` from branch `git_task` with `git checkout -b git_2`

![](media/preparations/16.png)

17. Check if all branches was created with `git show-branch`

![](media/preparations/17.png)

18. Push branches to remote repo with `git push --all -u`

![](media/preparations/18.png)

19. Check branches on the remote repo

![](media/preparations/19.png)

------------------------------------------------------------------

###3.2. Part 1

------------------------------------------------------------------

    Now proceed these steps:

    Step 1. git_1: Add and commit firstFile.txt file with 10 lines.

1. Switch to branch `git_1` with `git checkout git_1`

![](media/part1/step1/01.png)

2. Create file `firstFile.txt` with `touch firstFile.txt`

![](media/part1/step1/02.png)

3. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step1/03.png)

4. In opened window press `i` to enter edit mode. <br>
   Add `10 lines` to `firstFile.txt`. <br> 
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step1/04a.png)<br><b>Before editing</b>

![](media/part1/step1/04b.png)<br><b>After editing</b>

5. Stage file `firstFile.txt` with `git add firstFile.txt`

![](media/part1/step1/05.png)

6. Commit changes on file `firstFile.txt` with `git commit -m "[first-clone/git_1](firstFile.txt) created and 10 lines added"`

![](media/part1/step1/06.png)

7. Check log for changes with `git log --all --oneline`

![](media/part1/step1/07.png)

----------------------------------------------------------------------------------------------------

    Step 2. git_1: Add and commit secondFile.txt file with 10 lines.

1. Create file `secondFile.txt` with `touch secondFile.txt`

![](media/part1/step2/01.png)

2. Open file `secondFile.txt` with `vim secondFile.txt`

![](media/part1/step2/02.png)

3. In opened window press `i` to enter edit mode. <br>
   Add `10 lines` to `secondFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step2/03a.png)<br><b>Before editing</b>

![](media/part1/step2/03b.png)<br><b>After editing</b>

4. Stage file `secondFile.txt` with `git add secondFile.txt`

![](media/part1/step2/04.png)

5. Commit changes on file `secondFile.txt` with `git commit -m "[first-clone/git_1](secondFile.txt) created and 10 lines added`

![](media/part1/step2/05.png)

6. Check log for changes `git log --all --oneline`

![](media/part1/step2/06.png)

-----------------------------------------------------------------------------------------------------

      Step 3. `merge` branch git_1 to git_2

1. Switch to branch `git_2` with `git checkout git_2`

![](media/part1/step3/01.png)

2. Merge branch `git_1` to branch `git_2` with `git merge git_1 --no-ff`

![](media/part1/step3/02.png)

3. Edit commit message in opened file `MERGE_MSG.txt`. <br>
   Change first line with desirable message e.g. `[first-clone/git_2]|MERGE| branch [git_1] to branch [git_2]` <br>
   Save and exit.

![](media/part1/step3/03a.png)<br><b>Before editing</b>

![](media/part1/step3/03b.png)<br><b>After editing</b>

4. Check log for changes with `git log --all --oneline`

![](media/part1/step3/04.png)

-----------------------------------------------------------------------------------------------------

      Step 4. git_2: Update and commit any two lines in secondFile.txt.

1. Open file `secondFile.txt` with `vim secondFile.txt`

![](media/part1/step4/01.png)

2. In opened window press `i` to enter edit mode. <br>
   Edit `2 lines` in `secondFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step4/02a.png)<br><b>Before editing</b>

![](media/part1/step4/02b.png)<br><b>After editing</b>

3. Stage file `secondFile.txt` with `git add secondFile.txt`

![](media/part1/step4/03.png)

4. Commit changes on file `secondFile.txt` with `git commit -m "[first-clone/git_2](secondFile.txt) 2 lines edited"`

![](media/part1/step4/04.png)

5. Check log for changes with `git log --all --oneline`

![](media/part1/step4/05.png)

-----------------------------------------------------------------------------------------------------

      Step 5. git_1: Update and commit the same 2 lines with the different info in secondFile.txt

1. Switch to branch `git_1` with `git checkout git_1`

![](media/part1/step5/01.png)

2. Open file `secondFile.txt` with `vim secondFile.txt`

![](media/part1/step5/02.png)

3. In opened window press `i` to enter edit mode. <br>
   Edit `another 2 lines` in `secondFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step5/03a.png)<br><b>Before editing</b>

![](media/part1/step5/03b.png)<br><b>After editing</b>

4. Stage file `secondFile.txt` with `git add secondFile.txt`

![](media/part1/step5/04.png)

5. Commit changes on file `secondFile.txt` with `git commit -m "[first-clone/git_1](secondFile.txt) 2 lines edited"`

![](media/part1/step5/05.png)

6. Check log for changes with `git log --all --oneline`

![](media/part1/step5/06.png)

-----------------------------------------------------------------------------------------------------

      Step 6. merge branch git_2 to git_1, resolve conflict. Left all (4) modified lines. Commit.

1. Merge branch `git_2` to branch `git_1` with `git merge git_2 --no-ff`

![](media/part1/step6/01.png)

2. Open file `secondFile.txt` with `vim secondFile.txt`

![](media/part1/step6/02.png)

3. In opened window press `i` to enter edit mode. <br>
   Remove `<<<<<<< HEAD` at line 1, `=======` at line 4,  `>>>>>>> git_2` at line 7. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step6/03a.png)<br><b>Before editing</b>

![](media/part1/step6/03b.png)<br><b>After editing</b>

4. Stage file `secondFile.txt` with `git add secondFile.txt`

![](media/part1/step6/04.png)

5. Commit merging with `git commit -m "[first-clone/git_1]|MERGE| branch [git_2] to branch [git_1]"`

![](media/part1/step6/05.png)

6. Check log for changes with `git log --all --oneline`

![](media/part1/step6/06.png)

-----------------------------------------------------------------------------------------------------

      Step 7. git_1: Update and commit firstFile.txt file, modify two lines.

1. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step7/01.png)

2. In opened window press `i` to enter edit mode. <br>
   Edit `2 lines` in `firstFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step7/02a.png)<br><b>Before editing</b>

![](media/part1/step7/02b.png)<br><b>After editing</b>

3. Stage file `firstFile.txt` with `git add firstFile.txt`

![](media/part1/step7/03.png)

4. Commit changes on file `firstFile.txt` with `git commit -m "[first-clone/git_1](firstFile.txt) 2 lines edited"`

![](media/part1/step7/04.png)

5. Check log for changes with `git log --all --oneline`

![](media/part1/step7/05.png)

-----------------------------------------------------------------------------------------------------

      Step 8. git_1: Update and commit firstFile.txt file, modify another two lines.

1. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step8/01.png)

2. In opened window press `i` to enter edit mode. <br>
   Edit `another 2 lines` in `firstFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step8/02a.png)<br><b>Before editing</b>

![](media/part1/step8/02b.png)<br><b>After editing</b>

3. Stage file `firstFile.txt` with `git add firstFile.txt`

![](media/part1/step8/03.png)

4. Commit changes on file `firstFile.txt` with `git commit -m "[first-clone/git_1](firstFile.txt) another 2 lines edited"`

![](media/part1/step8/04.png)

5. Check log for changes with `git log --all --oneline`

![](media/part1/step8/05.png)

-----------------------------------------------------------------------------------------------------

      Step 9. Transfer changes of commit from Step 7 only to git_2, using format patch.

1. Open log with `git log --all --oneline`

![](media/part1/step9/01.png)

2. Find and copy hash of commit for patch, here `ba50b23`

![](media/part1/step9/02.png)

3. Create patch file of commit with `git format-patch -1 ba50b23`

![](media/part1/step9/03.png)

4. Copy patch filename from console, here `0001-first-clone-git_1-firstFile.txt-2-lines-edited.patch`

![](media/part1/step9/04.png)

5. Switch to branch `git_2` with `git checkout git_2`

![](media/part1/step9/05.png)

6. Apply patch with `git apply --verbose 0001-first-clone-git_1-firstFile.txt-2-lines-edited.patch`

![](media/part1/step9/06.png)

7. Stage changed files with `git add .`

![](media/part1/step9/07.png)

8. Commit changes on files with `git commit -m "[first-clone/git_1](firstFile.txt) patch applied"`

![](media/part1/step9/08.png)

9. Check log for changes with `git log --all --oneline`

![](media/part1/step9/09.png)

-----------------------------------------------------------------------------------------------------

      Step 10. Transfer changes of commit from Step 8 only to git_2, using cherrypick command.

1. Open log with `git log --all --oneline`

![](media/part1/step10/01.png)

2. Find and copy hash of commit, here `caac0ee`

![](media/part1/step10/02.png)

3. Cherry-pick commit with `git cherry-pick --edit caac0ee`

![](media/part1/step10/03.png)

4. Edit commit message in opened file `COMMIT_EDITMSG.txt`. <br>
   Change first line with desirable message e.g. `[first-clone/git_2](firstFile.txt) commit was cherry-picked from git1` <br>
   Save and exit.

![](media/part1/step10/04a.png)<br><b>Before editing</b>

![](media/part1/step10/04b.png)<br><b>After editing</b>

5. Check log for changes with `git log --all --oneline`

![](media/part1/step10/05.png)

-----------------------------------------------------------------------------------------------------

      Step 11. git_2: Concatenate the last two commits using reset + commit commands.

1. Undo changes of the last two commits (`351e35c` and `19c3e95`) with `git reset HEAD~2`

![](media/part1/step11/01.png)

2. Check log for changes with `git log --all --oneline`

![](media/part1/step11/02.png)

3. Stage changed files with `git add .`

![](media/part1/step11/03.png)

4. Commit changes on files with `git commit -m "[first-clone/git_2] concat of the last two commits"`

![](media/part1/step11/04.png)

5. Check log for changes with `git log --all --oneline`

![](media/part1/step11/05.png)

-----------------------------------------------------------------------------------------------------

      Step 12. git_2: Change date, author and message of the last commit and add non-empty thirdFile.txt file to it.

1. Create file `thirdFile.txt` with `touch thirdFile.txt`

![](media/part1/step12/01.png)

2. Open file `thirdFile.txt` with `vim thirdFile.txt`

![](media/part1/step12/02.png)

3. In opened window press `i` to enter edit mode. <br>
   Add text e.g. `This is the third file` in `thirdFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step12/03a.png)<br><b>Before editing</b>

![](media/part1/step12/03b.png)<br><b>After editing</b>

4. Stage file `thirdFile.txt` with `git add thirdFile.txt`

![](media/part1/step12/04.png)

5. Commit changes on file thirdFile with `git commit --amend --no-edit --author "Bob <bob@gmail.com>" --date=now -m
"[first-clone/git_2](thirdFile.txt) author, date, message changed; file added`

![](media/part1/step12/05.png)

6. Check log for changes with `git log --all`

![](media/part1/step12/06.png)

-----------------------------------------------------------------------------------------------------

      Step 13. git_2: Create a new commit that reverts changes of the last one.

1. Revert the last commit with `git revert HEAD~1`

![](media/part1/step13/01.png)

2. Edit commit message in opened file `COMMIT_EDITMSG.txt`. <br>
   Change first line with desirable message e.g. `This is revert of commit e249c53`. <br>
   Save and exit.

![](media/part1/step13/02a.png)<br><b>Before editing</b>

![](media/part1/step13/02b.png)<br><b>After editing</b>

3. Check log for changes with `git log --all --oneline`

![](media/part1/step13/03.png)

-----------------------------------------------------------------------------------------------------

      Step 14. git_2: Create and commit thirdFile.txt file.

1. Create file `thirdFile.txt` with `touch thirdFile.txt`

![](media/part1/step14/01.png)

2. Stage file `thirdFile.txt` with `git add thirdFile.txt`

![](media/part1/step14/02.png)

3. Commit changes on file `thirdFile.txt` with `git commit -m "[first-clone/git_2](thirdFile.txt) file created"`

![](media/part1/step14/03.png)

4. Check log for changes with `git log --all --oneline`

![](media/part1/step14/04.png)

-----------------------------------------------------------------------------------------------------

      Step 15. git_2: Run command that removes all changes of the last two commits.

1. Remove all changes of the last two commits with `git reset --hard HEAD~2`

![](media/part1/step15/01.png)

2. Check log for changes with `git log --all --oneline`

![](media/part1/step15/02.png)

-----------------------------------------------------------------------------------------------------

      Step 16. Synchronize git_1 and git_2 with a remote repository.

1. Switch to branch `git_1` with `git checkout git_1`

![](media/part1/step16/01.png)

2. Pull from remote with `git pull --verbose`


      NOTE! `git push` command in context of this task is combination of:
      `git fetch` - download objects and refs from remote repository
      `git merge` - join development histories of local and remote repositories together
      So combination `git fetch` && `git merge` can be used instead of `git pull`

<b>Let's pull with combination of `git fetch` && `git merge`:</b>

a. Download objects and refs from remote repository with `git fetch --verbose`

![](media/part1/step16/02a.png)

b. Join development histories for branch `git_1` of local and remote repos together with `git merge origin/git_1`

![](media/part1/step16/02b.png)

3. Push changes to remote with `git push`

![](media/part1/step16/03.png)

4. Switch to branch `git_1` with `git checkout git_2`

![](media/part1/step16/04.png)

5. Pull from remote with `git pull --verbose`

![](media/part1/step16/05.png)

6. Push changes to remote with `git push`

![](media/part1/step16/06.png)

-----------------------------------------------------------------------------------------------------

      Step 17. clone your project to another folder.

1. Change current directory to `root directory` with `cd ../../`

![](media/part1/step17/01.png)

2. Create new directory with `mkdir second-clone`

![](media/part1/step17/02.png)

3. Change current directory to `second-clone` with `cd second-clone/`

![](media/part1/step17/03.png)

4. Copy clone link from gitHub repository `https://github.com/jelrus/remote-repo.git`

![](media/part1/step17/04.png)

5. Clone project with `git clone https://github.com/jelrus/remote-repo.git`

![](media/part1/step17/05.png)

6. Change current directory to `remote-repo` with `cd remote-repo/`

![](media/part1/step17/06.png)

-----------------------------------------------------------------------------------------------------

      Step 18. folder2: git_1: Change two lines in firstFile.txt. commit + push.

1. Switch to branch `git_1` with `git checkout git_1`

![](media/part1/step18/01.png)

2. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step18/02.png)

3. In opened window press `i` to enter edit mode. <br>
   Edit `2 lines` in `firstFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step18/03a.png)<br><b>Before editing</b>

![](media/part1/step18/03b.png)<br><b>After editing</b>

4. Stage file `firstFile.txt` with `git add firstFile.txt`

![](media/part1/step18/04.png)

5. Commit changes on `firstFile.txt` with `git commit -m "[second-clone/git_1](firstFile.txt) 2 lines edited"`

![](media/part1/step18/05.png)

6. Push changes to remote with `git push`

![](media/part1/step18/06.png)

-----------------------------------------------------------------------------------------------------

      Step 19. folder1: git_1: Change another two lines in firstFile.txt.

1. Change current directory to the `first-clone/remote-repo/` with `cd ../../first-clone/remote-repo`

![](media/part1/step19/01.png)

2. Switch to branch `git_1` with `git checkout git_1`

![](media/part1/step19/02.png)

3. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step19/03.png)

4. In opened window press `i` to enter edit mode. <br>
   Edit `another 2 lines` in `firstFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step19/04a.png)<br><b>Before editing</b>

![](media/part1/step19/04b.png)<br><b>After editing</b>

5. Save local changes with `git stash`

![](media/part1/step19/05.png)

-----------------------------------------------------------------------------------------------------

      Step 20. folder1: git_1:
               - Change another line in firstFile.txt (not the same as in 18, 19).

1. Open file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step20/01.png)

2. In opened window press `i` to enter edit mode. <br>
   Edit `another 1 line` in `firstFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step20/02a.png)<br><b>Before editing</b>

![](media/part1/step20/02b.png)<br><b>After editing</b>

3. Stage file `firstFile.txt`

![](media/part1/step20/03.png)

4. Commit file `firstFile.txt` with `git commit -m "[folder1/git_1](firstFile.txt) line 9 edited"`

![](media/part1/step20/04.png)


               - merge changes from Step 18 (pull) without committing changes from Step 19 and any additional commits.

1. Pull from remote with `git pull origin git_1 --verbose`

![](media/part1/step20/05.png)

2. Edit commit message in opened file `MERGE.msg`. <br>
   Change first line with desirable message e.g. `[first-clone/git_1]|MERGE| branch git 1 remote to git 1 local`. <br>
   Save and exit.

![](media/part1/step20/06a.png)<br><b>Before editing</b>

![](media/part1/step20/06b.png)<br><b>After editing</b>

3. Check merging result in file `firstFile.txt` with `vim firstFile.txt`

![](media/part1/step20/07.png)

4. Check if changes applied properly.
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part1/step20/08.png)

5. Check log for changes 

    with `git log --all --oneline`

    ![](media/part1/step20/09a.png)

    with `git log --graph --oneline --all`

    ![](media/part1/step20/09b.png)


      - push without commit changes.

1. Push changes to remote with `git push`

![](media/part1/step20/10.png)

2. Check if file firstFile.txt in branch git_1 changed properly

![](media/part1/step20/11.png)


        - Return to local state of Step 19. (stash)

1. Local state can be restored<br><br>

    a. With `git stash pop` if you want to retrieve last stashed local state<br><br>

    ![](media/part1/step20/12a.png)

    b. If you want to retrieve specific stashed local state<br><br>

      Get stash list with `git stash list`<br><br>

      ![](media/part1/step20/12b1.png)<br><br>
       
      Copy id of stash, here id `stash@{0}` <br><br>

      ![](media/part1/step20/12b2.png) <br><br>
       
      Retrieve specific local state with `git stash pop stash@{0}`<br><br>

      ![](media/part1/step20/12b3.png)<br><br>
       
2. Resolve conflicts by keeping last push changes, stashed or merged changes. <br>
    Let's merge changes - keep both last push changes and stashed altogether.<br><br>

    Open file firstFile.txt with `git firstFile.txt`<br> <br>

    ![](media/part1/step20/13a.png)<br><br>

    In opened window press `i` to enter edit mode. <br>
    Remove `<<<<<<< HEAD`, `=======`,  `>>>>>>> git_1`. <br>
    Edit file `firstFile.txt` so it looks like changes were applied consequently. <br>
    Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window. <br><br>

    ![](media/part1/step20/13b1.png)<br><b>Before editing</b><br><br>

    ![](media/part1/step20/13b2.png)<br><b>After editing</b><br><br>

    Stage file `firstFile.txt` with `git add firstFile.txt`<br><br>

    ![](media/part1/step20/13c.png)<br><br>

    Commit changes on `firstFile.txt` with `git commit -m "[first-clone/git_1]|MERGE|(firstFile.txt) was merged with stashed file"`<br><br>

    ![](media/part1/step20/13d.png)

-----------------------------------------------------------------------------------------------------

###3.3. Part 2

------------------------------------------------------------------

     Step 1. Create git_3 branch from git_task. Checkout to git_3.

1. Switch to branch `git_task` with `git checkout git_task`

![](media/part2/step1/01.png)

2. Create new branch `git_3` with `git checkout -b git_3`

![](media/part2/step1/02.png)

---------------------------------------------------------------------------------------------

      Step 2. Add new empty file doubtingFile.txt and commit it.

1. Create new empty file `doubtingFile.txt` with `touch doubtingFile.txt`

![](media/part2/step2/01.png)

2. Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step2/02.png)

3. Commit file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) created"`

![](media/part2/step2/03.png)

4. Check log for changes `with git log --all --oneline`

![](media/part2/step2/04.png)

----------------------------------------------------------------------------------------------

      Step 3. Add a line to a file and commit changes. Do it 5 times. 
              You should end up with 5 lines in a file and 6 commits: 
              1 for creating an empty file and 5 for adding a line. 

1. Commit on the first line in file `doubtingFile.txt`

   Open file `doubtingFile.txt` with `vim doubtingFile.txt`

![](media/part2/step3/01a.png)

   In opened window press `i` to enter edit mode. <br>
   Add `line 1` in `doubtingFile.txt`. <br>
   Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part2/step3/02a1.png)<br><b>Before editing</b>

![](media/part2/step3/02a2.png)<br><b>After editing</b>

   Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step3/03a.png)

   Commit changes on file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) line 1 added"`

![](media/part2/step3/04a.png)

2. Commit on the second line in file `doubtingFile.txt`

   Open file `doubtingFile.txt` with `vim doubtingFile.txt`

![](media/part2/step3/01b.png)

In opened window press `i` to enter edit mode. <br>
Add `line 2` in `doubtingFile.txt`. <br>
Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part2/step3/02b1.png)<br><b>Before editing</b>

![](media/part2/step3/02b2.png)<br><b>After editing</b>

Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step3/03b.png)

Commit changes on file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) line 2 added"`

![](media/part2/step3/04b.png)

3. Commit on the third line in file `doubtingFile.txt`

   Open file `doubtingFile.txt` with `vim doubtingFile.txt`

![](media/part2/step3/01c.png)

In opened window press `i` to enter edit mode. <br>
Add `line 3` in `doubtingFile.txt`. <br>
Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part2/step3/02c1.png)<br><b>Before editing</b>

![](media/part2/step3/02c2.png)<br><b>After editing</b>

Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step3/03c.png)

Commit changes on file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) line 3 added"`

![](media/part2/step3/04c.png)

4. Commit on the fourth line in file `doubtingFile.txt`

Open file `doubtingFile.txt` with `vim doubtingFile.txt`

![](media/part2/step3/01d.png)

In opened window press `i` to enter edit mode. <br>
Add `line 4` in `doubtingFile.txt`. <br>
Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part2/step3/02d1.png)<br><b>Before editing</b>

![](media/part2/step3/02d2.png)<br><b>After editing</b>

Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step3/03d.png)

Commit changes on file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) line 4 added"`

![](media/part2/step3/04d.png)

5. Commit on the fifth line in file `doubtingFile.txt`

Open file `doubtingFile.txt` with `vim doubtingFile.txt`

![](media/part2/step3/01e.png)

In opened window press `i` to enter edit mode. <br>
Add `line 5` in `doubtingFile.txt`. <br>
Press `ESC` to leave edit mode. Press `SHIFT + ZZ` to save changes and close window.

![](media/part2/step3/02e1.png)<br><b>Before editing</b>

![](media/part2/step3/02e2.png)<br><b>After editing</b>

Stage file `doubtingFile.txt` with `git add doubtingFile.txt`

![](media/part2/step3/03e.png)

Commit changes on file `doubtingFile.txt` with `git commit -m "[first-clone/git_3](doubtingFile.txt) line 5 added"`

![](media/part2/step3/04e.png)

-----------------------------------------------------------------------------------------------

      Step 4. Check you log and copy it somewhere.

1. Check log for changes with `git log --all --oneline`

![](media/part2/step4/01.png)

2. Check log for changes with git `log --graph --oneline --all`

![](media/part2/step4/02.png)

3. Copy it somewhere

-----------------------------------------------------------------------------------------------

      Step 5. Launch interactive rebase for 5 last commits, squash all the latest commits into the first one. 
              Reword first commit. You should end up with 2 commits:
              one for creating an empty file and the second for adding 5 lines. 
              Second commit should have a new commit message.

1. Open interactive rebase for `five last commits` with `git rebase -i HEAD~5`

![](media/part2/step5/01.png)

2. Edit operations in opened file `git-rebase-todo.txt`. <br>
   Reword first commit. Change `pick` to `reword` on `line 1`. <br>
   Squash other commits. Change `pick` to `squash` on `lines 2, 3, 4, 5` respectively <br>
   Save and exit.

![](media/part2/step5/02a.png)<br><b>Before editing</b>

![](media/part2/step5/02b.png)<br><b>After editing</b>

   Edit commit message for the first commit in opened file `COMMIT_EDITMSG.txt`. <br>
   Save and exit.

![](media/part2/step5/02c.png)<br><b>Before editing</b>

![](media/part2/step5/02d.png)<br><b>After editing</b>

   Check if first commit edited properly and remove four other in opened file `COMMIT_EDITMSG.txt`. <br>
   Save and exit.

![](media/part2/step5/02e.png)<br><b>Before editing</b>

![](media/part2/step5/02f.png)<br><b>After editing</b>

--------------------------------------------------------------------------------------------------

      Step 6. Check your log and compare it with the previous one. 
              Look at the hash, date, commit message. Explain what changed and why.

   Let's compare log from step 4 with log from step 5

![](media/part2/step6/01.png)<br><b>Step 4. Log</b>

   Check log for changes with `git log --all --oneline`

![](media/part2/step6/02.png)<br><b>Step 5. Log</b>

   <u>**So what happened?**</u>
   
   Let's see simplified progress in `git_3` from log obtained in Step 4.

![](media/part2/step6/03.gif)
   
   As we can see each commit added independently of each other. <br>
   HEAD moves from `file creation` to `add the fifth line` commit. <br>
   Each commit has its own hash, date and message.

   Let's see rebase action simplified.

![](media/part2/step6/04.gif)

   As we can see each squashed in commit aggregates changes from squashed one.
   And by the end all the info merges into the aggregated commit which replaces the first one with new hash, date, 
   message or any other info that was picked in the edit stages.

---------------------------------------------------------------------------------------------------------

      Step 7. Check your reflog. Explain to your mentor what you can see and why.


   Check reflog for changes with `git reflog`

![](media/part2/step7/01.png)

   In reflog (reference log) we see that each one from five commits concatenates each one in next to be squashed in 
   commit. Which means that we aggregate changes from one to one squashed commits. As we can in previous log analysis.

---------------------------------------------------------------------------------------------------------