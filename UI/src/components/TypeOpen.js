import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Title from './Title';
import * as TypeAPI from '../utility/TypeAPI';
import * as LogAPI from '../utility/LogAPI';
import { useParams } from "react-router-dom";


export default function OpenType() {
  let { openTypeId } = useParams();

  React.useEffect(() => {
    const getTypes = async () => {
      const res = await TypeAPI.get(openTypeId);
      console.log(res)
      setTypes(res)
    };

    getTypes();
  }, []);

  React.useEffect(() => {
    const getLogs = async () => {
      const res = await LogAPI.getAll();
      console.log(res)
      setLogs(res)
    };
    getLogs();
  }, []);

  const [t, setTypes] = React.useState([])
  const [logs, setLogs] = React.useState([])


  return (
    <React.Fragment>
      <Title>Viewing Type ID {t.id}:</Title>
      <h3>Details:</h3>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Custom Attribute Name 1</TableCell>
            <TableCell>Custom Attribute Name 2</TableCell>
            <TableCell>Custom Attribute Name 3</TableCell>
            <TableCell>Custom Attribute Name 4</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
            <TableRow key={t.id}>
              <TableCell>{t.id}</TableCell>
              <TableCell>{t.typeName}</TableCell>
              <TableCell>{t.customAttribute1}</TableCell>
              <TableCell>{t.customAttribute2}</TableCell>
              <TableCell>{t.customAttribute3}</TableCell>
              <TableCell>{t.customAttribute4}</TableCell>
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
            if (l.typeId === t.id) {
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

    </React.Fragment>
  );
}