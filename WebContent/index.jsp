<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%
            String theme = (String) request.getParameter("theme");
            theme = theme == null ? "metro/blue" : theme;
            String permission = (String) request.getParameter("permission");
            permission = permission == null ? "2" : permission;
        %>


        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <!-- Include one of jTable styles and Theme. -->
        <link href="jtable.2.3.1/themes/<%=theme%>/jtable.css" rel="stylesheet" type="text/css" />
        <link href="jtable.2.3.1/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />

        <!-- Include jTable script file. -->
        <script src="jtable.2.3.1/jquery-1.8.2.js" type="text/javascript"></script>
        <script src="jtable.2.3.1/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
        <script src="jtable.2.3.1/jquery.jtable.js" type="text/javascript"></script>

        <link href="jtable.2.3.1/extensions/validation/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
        <script src="jtable.2.3.1/extensions/validation/jquery.validationEngine-en.js" type="text/javascript"></script>
        <script src="jtable.2.3.1/extensions/validation/jquery.validationEngine.js" type="text/javascript"></script>


        <script src="jtable.2.3.1/localization/jquery.jtable.custom1.js" type="text/javascript"></script>


        <script type="text/javascript">
            $(document).ready(function () {
                var perm=document.getElementById("permission").value;
                var createurl="CRUDController?action=create";
                var updateurl="CRUDController?action=update";
                var deleteurl="CRUDController?action=delete";
                var listurl="CRUDController?action=list";
                
                if(perm=='1'){createurl='';updateurl='';}
               
               
                $('#userData').jtable({
                    title: 'USER LIST',//Title
                    selecting: true, //Enable selecting 
                    paging: true, //Enable paging
                    pageSize: 10, //Set page size (default: 10)
                    pageSizes: [10, 25, 50, 100],//default: [10, 25, 50, 100, 250, 500]
                    pageList:'normal',//Possible Value minimal: Show only first, previous, next and last links.normal: Shows page numbers in addition to 'minimal'.
                    sorting: true, //Enable sorting
                    defaultSorting: 'userid ASC', //Set default sorting
                    columnResizable: true, //Actually, no need to set true since it's default
                    columnSelectable: true, //Actually, no need to set true since it's default
                    saveUserPreferences: true, //Actually, no need to set true since it's default
                    dialogShowEffect:'size', //default: 'fade' Some options are 'blind', 'bounce', 'clip', 'drop', 'explode', 'fold', 'highlight', 'puff', 'pulsate', 'scale', 'shake', 'size', 'slide'
                    dialogHideEffect:'size',//default: 'fade' Some options are 'blind', 'bounce', 'clip', 'drop', 'explode', 'fold', 'highlight', 'puff', 'pulsate', 'scale', 'shake', 'size', 'slide'
                    gotoPageArea:'combobox',// Possible Value combobox,textbox,none default: 'combobox'
                    multiSorting:true,//Default false  If this option is set to true, User can perform multiple sorting by holding CTRL key.
                    showCloseButton:false,// default:false Indicates that whether jTable will show a close button/icon for the table. When user clicks the close button, closeRequested event is raised. This option is used internally by jTable to close child tables.
                    selectOnRowClick:true,//Indicates that whether jTable allows user to select a row by clicking anywhere on the row.
                    openChildAsAccordion: true, //Enable this line to show child tabes as accordion style
                    //Override Delete Confirmation Message
                    deleteConfirmation: function(data) {
                        if(perm=='1') {
                            data.cancel = true;
                            data.cancelMessage = 'Permission Denied!';
                        }else{
                            data.deleteConfirmMessage = 'Are you sure to delete User ' + data.record.firstName + '?';   
                        }
                    },
                    ajaxSettings: {
                        type: 'GET',
                        dataType: 'json'
                    },//Ajax Type Call
                    actions: {
                        createAction:createurl,
                        updateAction:updateurl,
                        deleteAction:deleteurl,
                        listAction:listurl
                    },
                    fields: {
                        //CHILD TABLE DEFINITION FOR "IP"
                        IP: {
                            title: '',
                            width: '5%',
                            sorting: false,
                            edit: false,
                            create: false,
                            display: function (data) {
                                //Create an image that will be used to open child table
                                var $img = $('<img src="jtable.2.3.1/themes/basic/edit.png" title="Edit IP" />');
                                //Open child table when user clicks the image
                                $img.click(function () {
                                    $('#userData').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: data.record.firstName+'_'+data.record.userid + ' - IP',
                                        selecting: true, //Enable selecting 
                                        paging: true, //Enable paging
                                        pageSize: 5, //Set page size (default: 10)
                                        pageSizes: [5,10, 25, 50],//default: [10, 25, 50, 100, 250, 500]
                                        pageList:'normal',//Possible Value minimal: Show only first, previous, next and last links.normal: Shows page numbers in addition to 'minimal'.
                                        sorting: true, //Enable sorting
                                        defaultSorting: 'id ASC', //Set default sorting
                                        
                                        actions: {
                                            createAction:'CRUDController?action=createip',
                                            updateAction: 'CRUDController?action=updateip',
                                            deleteAction: 'CRUDController?action=deleteip',
                                            listAction: 'CRUDController?action=listip&userid='+data.record.userid
                                        },
                                        fields: {
                                            id: {
                                                title:'ID',
                                                visibility: 'fixed', //This column always will be shown
                                                key: true,
                                                list: true,
                                                create:false,
                                                edit:false
                                            },
                                            userid: {
                                                type: 'hidden',
                                                defaultValue: data.record.userid
                                            },
                                            ip: {
                                                title: 'IP',
                                                width: '30%',
                                                edit: true,
                                                create: true
                                            },
                                            req: {
                                                title: 'Date',
                                                width: '20%',
                                                type: 'date',
                                                displayFormat: 'yy-mm-dd',
                                                create: true,
                                                edit: true
                                            }
                                        },
                                        //Initialize validation logic when a form is created
                                        formCreated: function (event, data) {
                                            data.form.find('input[name="ip"]').addClass('validate[required]');
                                            data.form.find('input[name="req"]').addClass('validate[required,custom[date]]');
                                            data.form.validationEngine();
                                        },
                                        //Validate form when it is being submitted
                                        formSubmitting: function (event, data) {
                                            return data.form.validationEngine('validate');
                                        },
                                        //Dispose validation logic when form is closed
                                        formClosed: function (event, data) {
                                            data.form.validationEngine('hide');
                                            data.form.validationEngine('detach');
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                                });
                                //Return image to show on the User row
                                return $img;
                            }
                        },
                        userid: {
                            title:'ID',
                            visibility: 'fixed', //This column always will be shown
                            key: true,
                            list: true,
                            create:false,
                            edit:false
                        },
                        firstName: {
                            title: 'First Name',
                            width: '30%',
                            create:true,
                            edit:true
                        },
                        lastName: {
                            title: 'Last Name',
                            width: '30%',
                            create:true,
                            edit:true
                        },
                        email: {
                            title: 'Email',
                            width: '20%',
                            create:true,
                            edit: true
                        },
                        gender: {
                            title: 'Gender',
                            options: { 'M': 'Male', 'F': 'Female' },
                            list: true
                        },
                        education: {
                            title: 'Education',
                            list: true,
                            type: 'radiobutton',
                            options: [
                                { Value: '1', DisplayText: 'Primary school' },
                                { Value: '2', DisplayText: 'High school' },
                                { Value: '3', DisplayText: 'University' }
                            ]
                        },
                        about: {
                            title: 'Info',
                            type: 'textarea',
                            list: true,
                            display: function (data) {
                                if (data.record.about.length > 7) {
                                    return data.record.about.substr(0,7) + "...";
                                }
                                else {
                                    return data.record.about;
                                }
                            }
                        },
                        isActive: {
                            title: 'Status',
                            width: '15%',
                            type: 'checkbox',
                            values: { 'false': 'Passive', 'true': 'Active' },
                            defaultValue: 'true',
                            list: true
                        },
                        usertypeid: {
                            title: 'Usertype',
                            width: '12%',
                            options: 'CRUDController?action=usertypelist',
                            list: true
                        },
                        countryId: {
                            title: 'Country',
                            dependsOn: 'usertypeid', //Countries depends on Usertype. Thus, jTable builds cascade dropdowns!
                            options: function (data) {
                                //data.source == 'edit' || data.source == 'create'
                                return 'CRUDController?action=countrylist&usertypeid=' + data.dependedValues.usertypeid;
                            },
                            list: false
                        },stateId: {
                            title: 'State',
                            dependsOn: 'countryId', //Countries depends on Usertype. Thus, jTable builds cascade dropdowns!
                            options: function (data) {
                                //data.source == 'edit' || data.source == 'create'
                                return 'CRUDController?action=statelist&countryid=' + data.dependedValues.countryId;
                            },
                            list: false
                        },
                        req: {
                            title: 'Request',
                            width: '20%',
                            type: 'date',
                            displayFormat: 'yy-mm-dd',
                            create:false,
                            edit: true,
                            sorting: false
                        } ,
                        MyButton: {
                            title: '',
                            width: 'auto',
                            create:false,
                            edit: false,
                            sorting: false,
                            display: function(data) {
                                var $img = $('<img src="jtable.2.3.1/themes/basic/delete.png" title="View and edit information" />');
                                $img.click(function () {
                                    alert('Shankar'+data.record.userid);
                                    return 'CRUDController?action=test&userid='+data.record.userid;
                                });
                                return $img;
                            }
                        }               
                    },
                    //Initialize validation logic when a form is created
                    formCreated: function (event, data) {
                        data.form.find('input[name="firstName"]').addClass('validate[required]');
                        data.form.find('input[name="lastName"]').addClass('validate[required]');
                        data.form.find('input[name="email"]').addClass('validate[required,custom[email]]');
                        data.form.find('input[name="gender"]').addClass('validate[required]');
                        data.form.find('input[name="education"]').addClass('validate[required]');
                        data.form.find('input[name="about"]').addClass('validate[required]');
                        data.form.find('input[name="req"]').addClass('validate[required,custom[date]]');
                        data.form.validationEngine();
                    },
                    //Validate form when it is being submitted
                    formSubmitting: function (event, data) {
                      
                        return data.form.validationEngine('validate');
                       
                       
                    },
                    //Dispose validation logic when form is closed
                    formClosed: function (event, data) {
                        data.form.validationEngine('hide');
                        data.form.validationEngine('detach');
                    },
                    toolbar: {
                        hoverAnimation: true, //Enable/disable small animation on mouse hover to a toolbar item.
                        hoverAnimationDuration: 60, //Duration of the hover animation.
                        hoverAnimationEasing: undefined, //Easing of the hover animation. Uses jQuery's default animation ('swing') if set to undefined.
                        items: [{
                                tooltip: 'Click here to export this table to csv file',
                                icon: 'jtable.2.3.1/image/csv.png',
                                text: '',
                                click: function () {
                                    alert('SHANKAR');
                                }
                            },
                            {
                                tooltip: 'Click here to reload table',
                                icon: 'jtable.2.3.1/image/refresh.jpg',
                                text: '',
                                click: function () {
                                    $('#userData').jtable('load');
                                }
                            }] //Array of your custom toolbar items.
                    }
                });
               
 
                //Load all records when page is load
                $('#userData').jtable('load');
                
               
                //Filter
                $('#fname').keypress(function () {
                    $('#userData').jtable('load', {
                        firstName: $('#fname').val()
                    });
                });
            });
            
        </script>
    </head>
    <body> 

        <br/>
        <div style="width:90%;margin-right:5%;margin-left:5%;text-align:center;">
            <div>
                <form method="post">
                    <input type="hidden" name="permission" id="permission" value="<%=permission%>" >
                    <input type="text" name="fname" id="fname" >
                </form>
            </div>
            <div id="userData"></div>
        </div>
    </body>
</html>