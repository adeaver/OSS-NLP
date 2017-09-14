var App = React.createClass({
    render: function() {
        return (
            <div className="UserLogin">
                <CreationForm />
            </div>
        )
    }
});

var CreationForm = React.createClass({
    getInitialState: function() {
        return (
            {
                email:"",
                adminUsername:"",
                adminPassword:"",
                currentBetaKey:""
            }
        )
    },
    handleEmailChange: function(e) {
        this.setState({email:e.target.value})
    },
    handleAdminUsernameChange: function(e) {
        this.setState({adminUsername:e.target.value})
    },
    handleAdminPasswordChange: function(e) {
        this.setState({adminPassword:e.target.value})
    },
    handleSubmit: function() {
        var dataToSend = {
                "email": this.state.email
        }  

        var data = JSON.stringify(dataToSend)
        var out = ""

        $.ajax({
            type:"POST",
            async:false,
            contentType:"application/json",
            url:"/betainvite",
            username:this.state.adminUsername,
            password:this.state.adminPassword,
            data:data,
            success: function(rData) {
                alert("Successful!")
                console.log(rData)
                out = rData.key
            },
            error: function(jqXHR, exception, errorthrown) {
                console.log("Error Occurred")
                console.log(jqXHR)
                alert("Unsuccessful!");
            }
        });
        
        if(out !== "") {
            this.setState({currentBetaKey:out})
        }
    },
    render: function() {
        return (
            <div className="LoginForm">
                <input name="email" type="email" onChange={this.handleEmailChange} placeholder="email"/><br />
                <input name="adminusername" onChange={this.handleAdminUsernameChange} placeholder="Admin Username" /><br />
                <input name="adminpassword" onChange={this.handleAdminPasswordChange} placeholder="Admin Password" type="password" /><br />
                <button onClick={this.handleSubmit}>Submit</button>
                <DisplayBetaKey betaKey={this.state.currentBetaKey} />
            </div>
        )
    }
});

var DisplayBetaKey = React.createClass({
    render: function() {
        return (
            <div className="DisplayBeta">
                <h1>{this.props.betakey}</h1>
            </div>
        )
    }
})

ReactDOM.render(
  <App />,
  document.getElementById('content')
);
