import React from "react";
import { Avatar, Typography, Grid, Card, CardContent } from "@mui/material";
import AssetPieChart from "./Pie"; // Import the AssetPieChart component
import * as UserApi from "../utility/UserAPI"

import styles from "../style/Dashboard.module.css"; // Import the CSS module file

export default function Dashboard() {
  const [user, setUser] = React.useState({})

  React.useEffect (() => {
    const getUser = async () =>{
      const res = await UserApi.get(sessionStorage.getItem("username"));
      setUser(res);
    }
    getUser();
  },[]);

  return (
    <div className={styles.root}>
      <main>
        <Grid container spacing={10} justifyContent="left">
          <Grid item xs={12} sm={8} md={6}>
            <Typography variant="h4" gutterBottom>
              User Infomation
            </Typography>
            <Avatar alt={user.firstname} src="https://via.placeholder.com/150" />
            <Typography variant="h6">{user.firstname + " " + user.lastname}</Typography>
            {user.role === "ADMIN" && 
            <Typography variant="body2">
              You have an ADMIN role which gives you access to all available actions within this application.
            </Typography>
            }
            {user.role === "USER" && 
            <Typography variant="body2">
              You have an USER role gives you permission to search, add, edit and comment on Assets as well as to search for Types within this application.
            </Typography>
            }
            {user.role === "READER" && 
            <Typography variant="body2">
              You have an READER role gives you limited permission to search for Assets and Types in this application.
            </Typography>
            }
            
            <Typography variant="body2" color="textSecondary">
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <Typography variant="h4" gutterBottom>
              Asset Distribution
            </Typography>
            <Typography variant="body1" gutterBottom>
              This dashboard displays the distribution of assets by type.
            </Typography>
            <AssetPieChart />
          </Grid>
          <Grid item xs={12}>
            <Typography variant="h4" gutterBottom>
              Application Description
            </Typography>
            <Typography variant="body1" gutterBottom>
              Our project aims to create a fantastic website for any company, 
              not just software ones, to manage all their important stuff in one place. 
              Instead of just handling code, this website will organize everything, from documents to videos, 
              making it easy to find and use. You can save different types of assets, like documents or videos, 
              along with their details. Plus, you can connect them to show how they're related. 
              There will be three roles: Readers who can view, Users who can add or edit, and Admins who can do everything. 
              The website will have a powerful search tool and keep track of changes made, ensuring everyone stays informed. 
              Later on, we might even add more cool features!
            </Typography>
          </Grid>
        </Grid>
      </main>
    </div>
  );
}
