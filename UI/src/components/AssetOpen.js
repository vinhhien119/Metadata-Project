import React, { useState, useEffect } from 'react';
import SaveIcon from "@mui/icons-material/Save";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import FormControlLabel from '@mui/material/FormControlLabel'; 
import Checkbox from '@mui/material/Checkbox'; 
import Title from './Title';
import 'reactflow/dist/style.css';
import { useParams } from "react-router-dom";
import ReactFlow, { Background, Controls } from 'reactflow';
import * as AssetAPI from '../utility/AssetAPI';
import * as CommentAPI from '../utility/CommentAPI';
import * as LogAPI from '../utility/LogAPI';
import * as TypeAPI from '../utility/TypeAPI';

const OpenAsset = () => {
  const username = sessionStorage.getItem('username');
  const [save, setSave] = useState("Save");
  const [cancel, setCancel] = useState("Cancel");
  const currentDate = Date.now();

  const [a, setAssets] = React.useState([])
  const [logs, setLogs] = React.useState([])
  const [t, setType] = React.useState([])
  const [comments, setComments] = React.useState([])

  const [comment, setAssetComment] = useState("");
  const [time, setTime] = useState("");
  const [itemId, setItemId] = useState("");
  const [visibleComment, setVisibleComment] = useState(false); 
  
  let { openAssetId } = useParams();

  React.useEffect(() => {
    const fetchData = async () => {
      const [assetData, logData, allTypeData, commentData] = await Promise.all([
        AssetAPI.get(openAssetId),
        LogAPI.getAll(),
        TypeAPI.getAll(),
        CommentAPI.getAll()
      ]);
      setAssets(assetData);
      setLogs(logData);
      const typeData = allTypeData.find(type => type.typeName === assetData.type);
      setType(typeData);
      setComments(commentData);
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (save === "Saved") {
      const timer = setTimeout(() => {
        setSave("Save");
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [save]);

  useEffect(() => {
    if (cancel === "Cancelled") {
      const timer = setTimeout(() => {
        document.getElementById("cancel-button").style.backgroundColor = "white";
        document.getElementById("cancel-button").style.color = "blue";
        setCancel("Cancel");
      }, 1500);
      return () => clearTimeout(timer);
    }
  }, [cancel]);

  const handleSaveButtonClick = async (event) => {
    event.preventDefault();
    const currentTime = new Date().toISOString();

    try {
      const response = await CommentAPI.addComment({
        itemId: a.id,
        comment: comment,
        timestamp: currentTime,
        publicComment: visibleComment,
        username: username
      });
      
      if (!response.ok) {
        throw new Error('Failed to add comment');
      }
      resetValue()
      console.log('Comment added successfully');
      setComments(await CommentAPI.getAll());
      setLogs(await LogAPI.getAll());
      setSave("Saved");
    } catch (error) {
      console.error('Error adding comment:', error);
    }
  };

  const resetValue = () => {
    setItemId("");
    setAssetComment("");
    setTime("");
  }

  const edges = [{ id: '1-2', source: '1', target: '2', label: a.associationRelation1, type: 'straightedge' },
                  { id: '1-3', source: '1', target: '3', label: a.associationRelation2, type: 'straightedge' },
                  { id: '1-4', source: '1', target: '4', label: a.associationRelation3, type: 'straightedge' },
                  { id: '1-5', source: '1', target: '5', label: a.associationRelation4, type: 'straightedge' }
                ];
                
            
  const nodes = [{
                  id: '1',
                  data: { label: 'Title: ' + a.title },
                  position: { x: 220, y: 20 },
                  type: 'input',
                },
                a.association1 && a.association1.length > 0 && {
                  id: '2',
                  data: { label: 'Asset ID: ' + a.association1 },
                  position: { x: 220, y: 170 },
                },
                a.association2 && a.association2.length > 0 && {
                  id: '3',
                  data: { label: 'Asset ID: ' + a.association2 },
                  position: { x: 20, y: 170 },
                },
                a.association3 && a.association3.length > 0 && {
                  id: '4',
                  data: { label: 'Asset ID: ' + a.association3 },
                  position: { x: 420, y: 170 },
                },
                a.association4 && a.association4.length > 0 && {
                  id: '5',
                  data: { label: 'Asset ID: ' + a.association4 },
                  position: { x: 620, y: 170 },
                }
                ].filter(Boolean);

  return (
    <React.Fragment>
      <Title>Viewing Asset ID {a.id}:</Title>
      <h3>Details:</h3>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Type</TableCell>
            <TableCell>Link</TableCell>
            <TableCell>Title</TableCell>
            <TableCell>Author</TableCell>
            {t && (
              <>
                <TableCell>{t.customAttribute1}</TableCell>
                <TableCell>{t.customAttribute2}</TableCell>
                <TableCell>{t.customAttribute3}</TableCell>
                <TableCell>{t.customAttribute4}</TableCell>
              </>
             )}       
          </TableRow>
        </TableHead>
        <TableBody>
            <TableRow key={a.id}>
              <TableCell>{a.type}</TableCell>
              <TableCell>{a.link}</TableCell>
              <TableCell>{a.title}</TableCell>
              <TableCell>{a.author}</TableCell>
              <TableCell>{a.customAttribute1}</TableCell>
              <TableCell>{a.customAttribute2}</TableCell>
              <TableCell>{a.customAttribute3}</TableCell>
              <TableCell>{a.customAttribute4}</TableCell>
            </TableRow>
        </TableBody>
      </Table>
    
      <h3>Action Log:</h3>
      <Table size="small">
        <TableHead>
        <TableRow>
            <TableCell>Action ID</TableCell>
            <TableCell>Logged Action</TableCell>
            <TableCell align="right">Timestamp</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
        {logs.map((l) => {
          if (l.assetId === a.id) {
            return (
              <TableRow key={l.id}>
                <TableCell>{l.id}</TableCell>
                <TableCell>{l.action}</TableCell>
                <TableCell align="right">{l.timestamp}</TableCell>
              </TableRow>
            );
          } else {
            return null; 
          }
        })}
        </TableBody>
      </Table>

    <h3>Associations:</h3>
    <div style={{ height: 300 }}>
      <ReactFlow nodes={nodes} edges={edges}>
        <Background />
        <Controls />
      </ReactFlow>
    </div>

    <h3>Asset Comments:</h3>
      <Table size="small">
        <TableHead>
        <TableRow>
            <TableCell>User</TableCell>
            <TableCell>Comment</TableCell>
            <TableCell align="right">Timestamp</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {comments.map((c) => {
          if (c.itemId === a.id) {
            return (
              <TableRow key={c.id}>
              <TableCell>{c.username}</TableCell>
              <TableCell>{c.comment}</TableCell>
              <TableCell align="right">{c.timestamp}</TableCell>
            </TableRow>
            );
          } else {
            return null; 
          }
        })}
        </TableBody>
      </Table>
      <Box
      component="form"
      sx={{
        "& .MuiTextField-root": { m: 1, width: "40ch" },
        background: "white",
        width: "100%",
        maxWidth: "100%",
        margin: 0,
      }}
      noValidate
      autoComplete="off"
    >
      <Grid item xs={6}>
          <TextField
            id="outlined-textarea"
            placeholder= "Enter a comment here"
            multiline
            value={comment}
            onChange={(e) => setAssetComment(e.target.value)}
          />
        </Grid>
        <FormControlLabel
          control={<Checkbox checked={visibleComment} onChange={(e) => setVisibleComment(e.target.checked)} />}
          label="Make it public"
        />
        <Button
          variant="contained"
          endIcon={<SaveIcon />}
          style={{ background: "black" }}
          onClick={handleSaveButtonClick}
        >
          {save}
        </Button>
        
      </Box>
    </React.Fragment>
  );
};

export default OpenAsset;
