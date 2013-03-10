<style>
   <#include "style.css">
</style>

<!DOCTYPE html>
<body>
<h2>Crash report:</h2>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<table class="bordered-table zebra-striped">
    <tr>
        <td><h4>Time:</h4></td>
        <td>${entry.timestamp}</td>
    </tr>
    <tr>
        <td><h4>Version</h4></td>
        <td>${entry.versionName} (${entry.versionCode})</td>
    </tr>
    <tr>
        <td><h4>Android version:</h4></td>
        <td>${entry.androidVersion}</td>
    </tr>
    <tr>
        <td><h4>Brand:</h4></td>
        <td>${entry.brand}</td>
    </tr>
    <tr>
        <td>
            <h4>Stacktrace:</h4>
        </td>
        <td>
            <pre class="prettyprint">
            ${entry.stackTrace?replace("\\n", "<br />")?replace("\\t", "&nbsp;&nbsp;")}
            </pre>
        </td>
    </tr>
</table>

</body>