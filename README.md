# cordova-plugin-jsonFileMan

1. Write to a json file 

2. Read from a text file
 

To add the cordova plugin 
        
        cordova plugin add https://github.com/sachinranka4u/cordova-plugin-jsonFileMan
        
To Write to a txt file in external storage:

        var dataURL = JSON.stringify(aJSONObj);
        var jsondata = {};
        var filename = "newFile";
        if (filename.length > 0) {
	        jsondata = {filename: filename, overwrite: false, externalStorage: "external"};
        } else {
	        jsondata = {overwrite: false, externalStorage: "external"};
        }
        try{
        
          window.plugins.JSONFileRW.writeJSON(function (result) {
	         // File path where the fail is saved
          }, function (error) {
	         // do something on error
          }, dataURL, jsondata);
        } catch (error) {
          alert("Error occurred in write!");
        }


To read from the file:


        var readUrl = Path+"newFile.txt";
        var jsondata = {};
        var filename = "";
        if (filename.length > 0) {
          jsondata = {filename: filename, overwrite: true, externalStorage: "external"};
        } else {
          jsondata = {overwrite: true, externalStorage: "external"};
        }
        try {
          window.plugins.JSONFileRW.readJSON(function (result) {
            var resultdata = result.toString();
          }, function (error) {
            alert(error);
          }, readUrl, jsondata);
        } catch (error) {
          alert("File read error:\n" + error);
        }
