
import 'package:flutter/material.dart';

class DemoPage extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return DemoPageState();
  }

}

class DemoPageState extends State<DemoPage> {
  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.lightBlueAccent,
      child: Center(
        child: Text('flutter page',style: TextStyle(color: Colors.black,fontSize: 40),),
      ),
    );
  }
}



























