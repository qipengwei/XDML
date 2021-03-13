<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>排行榜</title>
</head>
<body>
     <h1>排行榜</h1>
     <div>

     </div>
     <table>
         <thead>
            <tr>
                <td>排名</td>
                <td>name</td>
                <td>score</td>
            </tr>
         </thead>
         <tbody>
            <#list items as item>
                <tr>
                    <td>${item?index}</td>
                    <td>${item.user.name}</td>
                    <td>${item.score}</td>
                </tr>
            </#list>

         </tbody>
     </table>
</body>
</html>