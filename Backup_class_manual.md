## _Backup_ class manual
### The following is an example of using all the methods of this class in Java.

```java
Backup bckp = new Backup();

bckp.createBackup("secretKey");    // crete backup from database to the .xml file
bckp.importBackup("secretKey");    // import backup to the database
```
* `"secretKey"` is the encryption / decryption password
> __WARNING:__ 
>
>If you enter a different secret key when restoring a backup than when it was created, the database will be restored with corrupted data.
> 
> In this case, under no circumstances should you make a copy of this data! Instead, restore the previous copy again with the correct password.
---
___NOTE:___
1. Database backups are stored in _backup.xml_ file in main directory. [See example](backup.xml).
2. During next backup new data are __overriding__ old backup. Pay attention to it.
3. When importing a backup to the database, <span style='color:red'>__all rows are emptied!__</span> Please backup your data before importing, keeping to the point 2.