import * as React from "react";
import { Grid, TextField, Typography, Button } from "@mui/material";
import * as SignUpAPI from "../utility/SignUpAPI";
import { styled } from "@mui/material/styles";
import MuiAppBar from "@mui/material/AppBar";


export default function SignUp() {
  const [firstName, setFirstname] = React.useState("");
  const [lastName, setLastname] = React.useState("");
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [retypePassword, setRetypePassword] = React.useState("");
  const [error, setError] = React.useState("");
  const [success, setSuccess] = React.useState(false);
  const role = "READER";

  const handleReturn = () => {
    window.location.href = "/";
  };

  const handleSignUp =  async () => {
    if (retypePassword === password) {
      const res = await SignUpAPI.register({
        username,
        password,
        firstName,
        lastName,
        role
      })

      if(res.message.toLowerCase().includes("already exist")){
        setSuccess(false);
        setError("User already exist please try Sign In");
        setUsername("");
        setPassword("");
        setRetypePassword("");
      } else {
        setSuccess(true)
        setError("");
        setUsername("");
        setPassword("");
        setRetypePassword("");
      }
    } else {
      setSuccess(false);
      setPassword("");
      setRetypePassword("");
      setError("Password not match !");
    }
  };

  const AppBar = styled(MuiAppBar)(({ theme }) => ({
    zIndex: theme.zIndex.drawer + 1,
    backgroundColor: "black",
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  }));

  return (
    <>
    <AppBar position="absolute">
      <Typography
        component="h1"
        variant="h4"
        color="inherit"
        noWrap
        sx={{ flexGrow: 1, fontFamily: "Calibri", 
          paddingLeft: "100px", 
        }}
      >
        <p>Sign Up</p>
      </Typography>      
    </AppBar>    
    <Grid
      container
      spacing={5}
      justifyContent="center"
      style={{ minHeight: "100vh", paddingTop: "10%", paddingBottom: "5%" }}
    >
      <Grid
        item
        xs={12}
        sm={6}
        md={4}
        spacing={5}
        justifyContent="center"
        textAlign="center"
      >
        <Typography variant="h4" style={{ paddingBottom: "5%", whiteSpace: "nowrap" }}>
          Create A Repository Account
        </Typography>
        {success === false ? (
          <p style={{ color: "red" }}>{error}</p>
        ) : (
          <p style={{ color: "Green" }}>Create new user success</p>
        )}
        <TextField
          label="First Name"
          placeholder="firstname"
          value={firstName}
          fullWidth
          onChange={(e) => setFirstname(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Last name"
          placeholder="lastname"
          value={lastName}
          fullWidth
          onChange={(e) => setLastname(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Username"
          placeholder="username"
          value={username}
          fullWidth
          onChange={(e) => setUsername(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Password"
          type="password"
          value={password}
          fullWidth
          onChange={(e) => setPassword(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Retype Password"
          type="password"
          placeholder="username"
          fullWidth
          value={retypePassword}
          onChange={(e) => setRetypePassword(e.target.value)}
          margin="normal"
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSignUp}
          fullWidth
          style={{  background: "black", marginTop: "5%",marginBottom: "5%" }}
        >
          SignUp
        </Button>
        <Button
          variant="contained"
          fullWidth
          onClick={handleReturn}
        >
          Return to login
        </Button>
      </Grid>
    </Grid>
    </>
  );
}
