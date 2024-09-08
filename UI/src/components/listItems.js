import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import LogoutIcon from "@mui/icons-material/Logout";
import { Link } from "react-router-dom";
import styles from "../style/listItems.module.css";
import NoteAddIcon from "@mui/icons-material/NoteAdd";
import NoteAddOutlinedIcon from "@mui/icons-material/NoteAddOutlined";
import FindInPageIcon from "@mui/icons-material/FindInPage";
import FindInPageOutlinedIcon from "@mui/icons-material/FindInPageOutlined";
import ViewListTwoToneIcon from "@mui/icons-material/ViewListTwoTone";
import FindInPageTwoToneIcon from "@mui/icons-material/FindInPageTwoTone";


function handleLogout() {
  sessionStorage.clear();
  console.log(sessionStorage.role)
}

export const adminListItems = (
  <React.Fragment>
    <Link to="/welcome" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="Dashboard" />
      </ListItemButton>
    </Link>
    <Link to="/asset/add" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <NoteAddIcon />
        </ListItemIcon>
        <ListItemText primary="Add Asset" />
      </ListItemButton>
    </Link>
    <Link to="/type/add" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <NoteAddOutlinedIcon />
        </ListItemIcon>
        <ListItemText primary="Add Type" />
      </ListItemButton>
    </Link>
    <Link to="/asset/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageIcon />
        </ListItemIcon>
        <ListItemText primary="Find Asset" />
      </ListItemButton>
    </Link>
    <Link to="/type/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageOutlinedIcon />
        </ListItemIcon>
        <ListItemText primary="Find Type" />
      </ListItemButton>
    </Link>
    <Link to="/user/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageTwoToneIcon />
        </ListItemIcon>
        <ListItemText primary="Find User" />
      </ListItemButton>
    </Link>
    <Link to="/log/view" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <ViewListTwoToneIcon />
        </ListItemIcon>
        <ListItemText primary="View Logs" />
      </ListItemButton>
    </Link>
    <Link to="/" className={styles.link} onClick={handleLogout}>
      <ListItemButton>
        <ListItemIcon>
          <LogoutIcon />
        </ListItemIcon>
        <ListItemText primary="Log out" />
      </ListItemButton>
    </Link>
  </React.Fragment>
);

export const userListItems = (
  <React.Fragment>
    <Link to="/welcome" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="Dashboard" />
      </ListItemButton>
    </Link>
    <Link to="/asset/add" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <NoteAddIcon />
        </ListItemIcon>
        <ListItemText primary="Add Asset" />
      </ListItemButton>
    </Link>
    <Link to="/asset/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageIcon />
        </ListItemIcon>
        <ListItemText primary="Find Asset" />
      </ListItemButton>
    </Link>
    <Link to="/type/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageOutlinedIcon />
        </ListItemIcon>
        <ListItemText primary="Find Type" />
      </ListItemButton>
    </Link>
    <Link to="/" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <LogoutIcon />
        </ListItemIcon>
        <ListItemText primary="Log out" />
      </ListItemButton>
    </Link>
  </React.Fragment>
);

export const readerListItems = (
  <React.Fragment>
    <Link to="/welcome" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="Dashboard" />
      </ListItemButton>
    </Link>
    <Link to="/asset/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageIcon />
        </ListItemIcon>
        <ListItemText primary="Find Asset" />
      </ListItemButton>
    </Link>
    <Link to="/type/find" className={styles.link}>
      <ListItemButton>
        <ListItemIcon>
          <FindInPageOutlinedIcon />
        </ListItemIcon>
        <ListItemText primary="Find Type" />
      </ListItemButton>
    </Link>
    <Link to="/" className={styles.link} onClick={handleLogout}>
      <ListItemButton>
        <ListItemIcon>
          <LogoutIcon />
        </ListItemIcon>
        <ListItemText primary="Log out" />
      </ListItemButton>
    </Link>
  </React.Fragment>
);
