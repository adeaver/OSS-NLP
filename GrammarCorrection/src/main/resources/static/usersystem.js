var App = React.createClass({
    render: function() {
        return (
            <div className="UserLogin">
                <CreationForm />
                <hr />
                <LoginForm />
            </div>
        )
    }
});

var CreationForm = React.createClass({
    getInitialState: function() {
        return (
            {
                username:"",
                password:"",
                email:"",
                lang:"",
                adminUsername:"",
                adminPassword:""
            }
        )
    },
    handleUsernameChange: function(e) {
        this.setState({username:e.target.value})
    },
    handlePasswordChange: function(e) {
        this.setState({password:e.target.value})
    },
    handleEmailChange: function(e) {
        this.setState({email:e.target.value})
    },
    handleLangChange: function(e) {
        this.setState({lang:e.target.value})
    },
    handleAdminUsernameChange: function(e) {
        this.setState({adminUsername:e.target.value})
    },
    handleAdminPasswordChange: function(e) {
        this.setState({adminPassword:e.target.value})
    },
    handleSubmit: function() {
        var dataToSend = {
                "username":this.state.username,
                "password":this.state.password,
                "lang": this.state.lang,
                "email": this.state.email
        }  

        var data = JSON.stringify(dataToSend)

        $.ajax({
            type:"POST",
            async:false,
            contentType:"application/json",
            url:"/user",
            username:this.state.adminUsername,
            password:this.state.adminPassword,
            data:data,
            success: function(data) {
                alert("Successful!");
            },
            error: function(jqXHR, exception) {
                var error = JSON.parse(jqXHR.responseText);
                alert(error.message);
            }
        });
    },
    render: function() {
        return (
            <div className="LoginForm">
                <input name="username" onChange={this.handleUsernameChange} placeholder="username" /><br />
                <input name="password" type="password" onChange={this.handlePasswordChange} placeholder="password" /><br />
                <input name="lang" onChange={this.handleLangChange} placeholder="language"/><br />
                <input name="email" type="email" onChange={this.handleEmailChange} placeholder="email"/><br />
                <input name="adminusername" onChange={this.handleAdminUsernameChange} placeholder="Admin Username" /><br />
                <input name="adminpassword" onChange={this.handleAdminPasswordChange} placeholder="Admin Password" type="password" /><br />
                <button onClick={this.handleSubmit}>Submit</button>
            </div>
        )
    }
});

var LoginForm = React.createClass({
    getInitialState: function() {
        return ({
            username:"",
            password:""
        })
    },
    handleUsernameChange: function(e) {
        this.setState({username:e.target.value})
    },
    handlePasswordChange: function(e) {
        this.setState({password:e.target.value})
    },
    handleSubmit: function(e) {
        var dataToSend = {
            username:this.state.username,
            password:this.state.password
        }

        var data = JSON.stringify(dataToSend);        

        $.ajax({
            type:"POST",
            async:false,
            contentType:"application/json",
            url:"/user/login",
            username:this.state.adminUsername,
            password:this.state.adminPassword,
            data:data,
            success: function(data) {
                alert("Successful!");
            },
            error: function(jqXHR, exception) {
                var error = JSON.parse(jqXHR.responseText);
                alert(error.message);
            }
        });
    },
    render: function() {
        return (
            <div className="LoginF">
                <input name="login_username" onChange={this.handleUsernameChange} placeholder="username" /><br />
                <input name="login_password" type="password" onChange={this.handlePasswordChange} placeholder="password" /><br />
                <button onClick={this.handleSubmit}>Submit</button>
            </div>
        )
    }
});

ReactDOM.render(
  <App />,
  document.getElementById('content')
);
